package com.javiersc.gradle.plugins.code.analysis

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import java.io.File
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.sonarqube.gradle.SonarQubeExtension

abstract class CodeAnalysisPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.configureDetekt()
        target.configureSonarqube()
    }
}

private fun Project.configureDetekt() {
    pluginManager.apply("io.gitlab.arturbosch.detekt")
    extensions.findByType(DetektExtension::class.java)?.apply {
        parallel = true
        isIgnoreFailures = true
        buildUponDefaultConfig = true
        basePath = rootProject.projectDir.path
    }

    tasks.withType(Detekt::class.java).configureEach { detekt ->
        detekt.setSource(detekt.project.files(detekt.project.projectDir))
        detekt.include("**/*.kt")
        detekt.include("**/*.kts")
        detekt.exclude("**/resources/**")
        detekt.exclude("**/build/**")

        detekt.reports { reports ->
            reports.html { report -> report.required.set(true) }
            reports.sarif { report -> report.required.set(true) }
            reports.txt { report -> report.required.set(false) }
            reports.xml { report -> report.required.set(false) }
        }
    }

    File("${rootProject.rootDir}/.idea/detekt.xml").apply {
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

private fun Project.configureSonarqube() {
    pluginManager.apply("org.sonarqube")

    project.extensions.findByType(SonarQubeExtension::class.java)?.apply {
        properties { props ->
            props.property(
                "sonar.projectKey",
                properties["codeAnalysis.sonar.projectKey"]
                    ?: "${group}:${properties["project.name"]}"
            )
            props.property("sonar.login", properties["codeAnalysis.sonar.login"] ?: "")
            props.property(
                "sonar.host.url",
                properties["codeAnalysis.sonar.host.url"] ?: "https://sonarcloud.io"
            )
            props.property(
                "sonar.organization",
                properties["codeAnalysis.sonar.organization"] ?: ""
            )
            props.property(
                "sonar.coverage.jacoco.xmlReportPaths",
                "$buildDir/reports/kover/report.xml"
            )
        }
    }

    allprojects { allProject ->
        allProject.afterEvaluate { project ->
            project.extensions.findByType(SonarQubeExtension::class.java)?.apply {
                properties { properties ->
                    properties.property("sonar.sources", project.kotlinSrcDirs())
                }
            }
        }
    }
}

private fun Project.kotlinSrcDirs(): List<File> =
    extensions
        .findByType(KotlinProjectExtension::class.java)
        ?.sourceSets
        ?.flatMap { kotlinSourceSet -> kotlinSourceSet.kotlin.srcDirs }
        ?.filterNot { file ->
            val relativePath = file.relativeTo(projectDir)
            val dirs = relativePath.path.split(File.separatorChar)
            dirs.any { dir -> dir.endsWith("Test") || dir == "test" }
        }
        ?.filter { file -> file.exists() }
        .orEmpty()
