package com.javiersc.gradle.plugins.code.formatter

import com.diffplug.gradle.spotless.SpotlessExtension
import java.io.File
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper

abstract class CodeFormatterPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply("com.diffplug.spotless")

        target.extensions.findByType(SpotlessExtension::class.java)?.apply {
            kotlin {
                it.target(
                    "*/kotlin/**/*.kt",
                    "src/*/kotlin/**/*.kt",
                )
                it.targetExclude(
                    "*/resources/**/*.kt",
                    "src/*/resources/**/*.kt",
                    "**/build/**",
                    "**/.gradle/**",
                )
                it.ktfmt(KTFMT_VERSION).kotlinlangStyle()
            }
        }

        File("${target.rootProject.rootDir}/.idea/ktfmt.xml").apply {
            parentFile.mkdirs()
            createNewFile()
            writeText(
                """
                    <?xml version="1.0" encoding="UTF-8"?>
                    <project version="4">
                      <component name="KtfmtSettings">
                        <option name="enabled" value="true" />
                        <option name="uiFormatterStyle" value="Kotlinlang" />
                      </component>
                    </project>
                """.trimIndent()
            )
        }

        target.allprojects { project ->
            project.afterEvaluate {
                if (it.hasKotlin) it.plugins.apply("com.javiersc.gradle.plugins.code.formatter")
            }
        }
    }
}

private val Project.hasKotlin: Boolean
    get() = plugins.asSequence().mapNotNull { (it as? KotlinBasePluginWrapper) }.count() > 0
