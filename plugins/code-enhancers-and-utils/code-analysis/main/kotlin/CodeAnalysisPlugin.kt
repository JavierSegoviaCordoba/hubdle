package com.javiersc.gradle.plugins.code.analysis

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import java.io.File
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class CodeAnalysisPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply("io.gitlab.arturbosch.detekt")
        target.extensions.findByType(DetektExtension::class.java)?.apply {
            parallel = true
            isIgnoreFailures = true
            buildUponDefaultConfig = true
            basePath = target.rootProject.projectDir.path
        }

        target.tasks.withType(Detekt::class.java).configureEach { detekt ->
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
}
