package com.javiersc.gradle.plugins.code.formatter

import com.diffplug.gradle.spotless.SpotlessExtension
import java.io.File
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper

abstract class CodeFormatterPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        check(target == target.rootProject) {
            "`com.javiersc.gradle.plugins.code.formatter` must be applied in the root project"
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

        target.allprojects { allProject ->
            allProject.afterEvaluate { project ->
                if (project.hasKotlin) {
                    project.pluginManager.apply("com.diffplug.spotless")

                    project.extensions.findByType<SpotlessExtension>()?.apply {
                        kotlin { kotlinExtension ->
                            kotlinExtension.target(
                                "*/kotlin/**/*.kt",
                                "src/*/kotlin/**/*.kt",
                            )
                            kotlinExtension.targetExclude(
                                "*/resources/**/*.kt",
                                "src/*/resources/**/*.kt",
                                "**/build/**",
                                "**/.gradle/**",
                            )
                            kotlinExtension.ktfmt(KTFMT_VERSION).kotlinlangStyle()
                        }
                    }
                }
            }
        }
    }
}

private val Project.hasKotlin: Boolean
    get() = plugins.asSequence().mapNotNull { (it as? KotlinBasePluginWrapper) }.count() > 0
