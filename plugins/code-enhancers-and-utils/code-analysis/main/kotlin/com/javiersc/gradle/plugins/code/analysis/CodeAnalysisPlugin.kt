package com.javiersc.gradle.plugins.code.analysis

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import java.io.File
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.sonarqube.gradle.SonarQubeExtension

abstract class CodeAnalysisPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        configureDetekt(target)
        configureSonarqube(target)
    }
}

private fun configureDetekt(target: Project) {
    target.pluginManager.apply("io.gitlab.arturbosch.detekt")
    target.extensions.findByType<DetektExtension>()?.apply {
        parallel = true
        isIgnoreFailures = true
        buildUponDefaultConfig = true
        basePath = target.rootProject.projectDir.path
    }

    target.tasks.withType<Detekt>() {
        setSource(project.files(project.projectDir))
        include("**/*.kt")
        include("**/*.kts")
        exclude("**/resources/**")
        exclude("**/build/**")

        reports { reports ->
            reports.html { report -> report.required.set(true) }
            reports.sarif { report -> report.required.set(true) }
            reports.txt { report -> report.required.set(false) }
            reports.xml { report -> report.required.set(true) }
        }
    }

    File("${target.rootProject.rootDir}/.idea/detekt.xml").apply {
        parentFile.mkdirs()
        createNewFile()
        writeText(
            """
                <?xml version="1.0" encoding="UTF-8"?>
                <project version="4">
                  <component name="DetektProjectConfiguration">
                    <enableDetekt>true</enableDetekt>
                    <enableFormatting>true</enableFormatting>
                    <buildUponDefaultConfig>true</buildUponDefaultConfig>
                  </component>
                </project>
            """.trimIndent()
        )
    }
}

private fun configureSonarqube(target: Project) {
    target.pluginManager.apply("org.sonarqube")

    target.extensions.findByType<SonarQubeExtension>()?.apply {
        properties { props ->
            props.property(
                "sonar.projectKey",
                target.properties["codeAnalysis.sonar.projectKey"]
                    ?: "${target.group}:${target.properties["project.name"]}"
            )
            props.property(
                "sonar.login",
                target.properties["codeAnalysis.sonar.login"] ?: System.getenv("SONAR_TOKEN") ?: ""
            )
            props.property(
                "sonar.host.url",
                target.properties["codeAnalysis.sonar.host.url"] ?: "https://sonarcloud.io"
            )
            props.property(
                "sonar.organization",
                target.properties["codeAnalysis.sonar.organization"] ?: ""
            )
            props.property(
                "sonar.kotlin.detekt.reportPaths",
                "${target.buildDir}/reports/detekt/detekt.xml"
            )
            props.property(
                "sonar.coverage.jacoco.xmlReportPaths",
                "${target.buildDir}/reports/kover/report.xml"
            )
        }
    }

    target.allprojects { project ->
        project.afterEvaluate { evaluatedProject ->
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
