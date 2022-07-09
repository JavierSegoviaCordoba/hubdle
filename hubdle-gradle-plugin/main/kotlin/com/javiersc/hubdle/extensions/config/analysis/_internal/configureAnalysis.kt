package com.javiersc.hubdle.extensions.config.analysis._internal

import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.gradle.properties.extensions.getProperty
import com.javiersc.gradle.properties.extensions.getPropertyOrNull
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.HubdleProperty
import com.javiersc.hubdle.HubdleProperty.Analysis
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import java.io.File
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.sonarqube.gradle.SonarQubeExtension

internal fun configureAnalysis(project: Project) {
    if (project.hubdleState.config.analysis.isEnabled) {
        check(project.isRootProject) {
            """Hubdle `analysis()` must be only configured in the root project"""
        }
        val checkAnalysisTask = project.tasks.maybeRegisterLazily<Task>("checkAnalysis")
        checkAnalysisTask.configureEach { group = "verification" }
        project.tasks.namedLazily<Task>("check").configureEach { dependsOn(checkAnalysisTask) }

        configureDetekt(project)
        configureSonarqube(project)
    }
}

internal fun configureConfigAnalysisRawConfig(project: Project) {
    project.hubdleState.config.analysis.rawConfig.detekt?.execute(project.the())
}

private fun configureDetekt(project: Project) {
    project.pluginManager.apply(PluginIds.Analysis.detekt)

    with(project.hubdleState.config) {
        project.extensions.findByType<DetektExtension>()?.apply {
            parallel = true
            isIgnoreFailures = analysis.ignoreFailures
            buildUponDefaultConfig = true
            basePath = project.rootProject.projectDir.path
        }

        project.tasks.namedLazily<Task>("checkAnalysis").configureEach { dependsOn("detekt") }

        project.tasks.withType<Detekt>().configureEach {
            setSource(project.files(project.rootDir))
            include(analysis.includes.distinct())
            exclude(analysis.excludes.distinct())

            reports {
                html.required.set(analysis.reports.html)
                sarif.required.set(analysis.reports.sarif)
                txt.required.set(analysis.reports.txt)
                xml.required.set(analysis.reports.xml)
            }
        }
    }
}

private fun configureSonarqube(project: Project) {
    project.pluginManager.apply(PluginIds.Analysis.sonarqube)

    // project.tasks.namedLazily<Task>("sonarqube").configureEach { it.dependsOn("detekt") }

    // project.tasks.namedLazily<Task>("checkAnalysis").configureEach { it.dependsOn("sonarqube") }

    project.configure<SonarQubeExtension> {
        properties {
            property(
                "sonar.projectName",
                project.getPropertyOrNull(Analysis.projectName)
                    ?: project.getPropertyOrNull(HubdleProperty.Project.rootProjectDirName)
                        ?: project.name
            )
            property(
                "sonar.projectKey",
                project.getPropertyOrNull(Analysis.projectKey)
                    ?: project.getPropertyOrNull(HubdleProperty.Project.rootProjectDirName)
                        ?: "${project.group}:${project.name}"
            )
            property(
                "sonar.login",
                project.getProperty(Analysis.login),
            )
            property(
                "sonar.host.url",
                project.getPropertyOrNull(Analysis.hostUrl) ?: "https://sonarcloud.io"
            )

            property("sonar.organization", project.getPropertyOrNull(Analysis.organization) ?: "")
            property(
                "sonar.kotlin.detekt.reportPaths",
                "${project.buildDir}/reports/detekt/detekt.xml"
            )
            property(
                "sonar.coverage.jacoco.xmlReportPaths",
                "${project.buildDir}/reports/kover/report.xml"
            )
        }
    }

    project.allprojects {
        afterEvaluate {
            extensions.findByType<SonarQubeExtension>()?.apply {
                properties {
                    property("sonar.sources", kotlinSrcDirs())
                    property("sonar.tests", kotlinTestsSrcDirs())
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
