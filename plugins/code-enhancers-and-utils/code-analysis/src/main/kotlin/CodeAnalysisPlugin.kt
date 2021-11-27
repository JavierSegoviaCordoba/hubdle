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

        target.tasks.withType(Detekt::class.java).configureEach {
            setSource(project.files(project.projectDir))
            include("**/*.kt")
            include("**/*.kts")
            exclude("**/resources/**")
            exclude("**/build/**")

            reports {
                html { enabled = true }
                sarif { enabled = true }
                txt { enabled = false }
                xml { enabled = false }
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
