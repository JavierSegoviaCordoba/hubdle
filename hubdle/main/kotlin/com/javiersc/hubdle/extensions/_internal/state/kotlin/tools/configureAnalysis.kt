package com.javiersc.hubdle.extensions._internal.state.kotlin.tools

import com.javiersc.gradle.extensions.maybeRegisterLazily
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.properties.PropertyKey.Sonar
import com.javiersc.hubdle.properties.getProperty
import com.javiersc.hubdle.properties.getPropertyOrNull
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import java.io.File
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.sonarqube.gradle.SonarQubeExtension

internal fun configureAnalysis(project: Project) {
    if (project.hubdleState.kotlin.tools.analysis.isEnabled) {
        configureDetekt(project)
        configureSonarqube(project)
    }
}

private fun configureDetekt(project: Project) {
    project.pluginManager.apply(PluginIds.Analysis.detekt)

    with(project.hubdleState.kotlin.tools) {
        project.extensions.findByType<DetektExtension>()?.apply {
            parallel = true
            isIgnoreFailures = analysis.isIgnoreFailures
            buildUponDefaultConfig = true
            basePath = project.rootProject.projectDir.path
        }

        project.tasks.maybeRegisterLazily<Task>("checkAnalysis").configureEach {
            it.group = "verification"
            it.dependsOn("detekt")
        }

        project.tasks.withType<Detekt>().configureEach { detekt ->
            detekt.setSource(project.files(project.projectDir))
            detekt.include(analysis.includes)
            detekt.exclude(analysis.excludes)

            detekt.reports { reports ->
                reports.html { report -> report.required.set(analysis.reports.html) }
                reports.sarif { report -> report.required.set(analysis.reports.sarif) }
                reports.txt { report -> report.required.set(analysis.reports.txt) }
                reports.xml { report -> report.required.set(analysis.reports.xml) }
            }
        }
    }
}

private fun configureSonarqube(project: Project) {
    project.pluginManager.apply(PluginIds.Analysis.sonarqube)

    project.configure<SonarQubeExtension>() {
        properties { props ->
            props.property(
                "sonar.projectKey",
                project.getPropertyOrNull(Sonar.projectKey) ?: "${project.group}:${project.name}"
            )
            props.property("sonar.login", project.getProperty(Sonar.login))
            props.property(
                "sonar.host.url",
                project.getPropertyOrNull(Sonar.hostUrl) ?: "https://sonarcloud.io"
            )

            props.property(
                "sonar.organization",
                project.getPropertyOrNull(Sonar.organization) ?: ""
            )
            props.property(
                "sonar.kotlin.detekt.reportPaths",
                "${project.buildDir}/reports/detekt/detekt.xml"
            )
            props.property(
                "sonar.coverage.jacoco.xmlReportPaths",
                "${project.buildDir}/reports/kover/report.xml"
            )
        }
    }

    project.allprojects { proj ->
        proj.afterEvaluate { evaluatedProject ->
            evaluatedProject.extensions.findByType<SonarQubeExtension>()?.apply {
                properties { properties ->
                    properties.property("sonar.sources", evaluatedProject.kotlinSrcDirs())
                    properties.property("sonar.tests", evaluatedProject.kotlinTestsSrcDirs())
                }
            }
        }
    }
}

private fun Project.kotlinSrcDirs(): List<File> =
    extensions
        .findByType<KotlinProjectExtension>()
        ?.sourceSets
        ?.flatMap { kotlinSourceSet -> kotlinSourceSet.kotlin.srcDirs }
        ?.filterNot { file ->
            val relativePath = file.relativeTo(projectDir)
            val dirs = relativePath.path.split(File.separatorChar)
            dirs.any { dir -> dir.endsWith("Test") || dir == "test" }
        }
        ?.filter { file -> file.exists() }
        .orEmpty()

private fun Project.kotlinTestsSrcDirs(): List<File> =
    extensions
        .findByType<KotlinProjectExtension>()
        ?.sourceSets
        ?.flatMap { kotlinSourceSet -> kotlinSourceSet.kotlin.srcDirs }
        ?.filter { file ->
            val relativePath = file.relativeTo(projectDir)
            val dirs = relativePath.path.split(File.separatorChar)
            dirs.any { dir -> dir.endsWith("Test") || dir == "test" }
        }
        ?.filter { file -> file.exists() }
        .orEmpty()
