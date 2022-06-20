@file:OptIn(ExperimentalStdlibApi::class)

package com.javiersc.hubdle.extensions.config.documentation.site._internal

import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.documentation.site.BuildSiteTask
import com.javiersc.hubdle.extensions.config.documentation.site.PrebuildSiteTask
import com.javiersc.hubdle.extensions.config.documentation.site.projectsInfo
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper
import ru.vyarus.gradle.plugin.mkdocs.MkdocsExtension

internal fun configureSite(project: Project) {
    if (project.hubdleState.config.documentation.site.isEnabled) {
        check(project.isRootProject) {
            """
                |`site` can be only used in the root project, and it is being applied in:
                |  - `${project.path}
            """.trimMargin()
        }

        project.pluginManager.apply(PluginIds.Documentation.mkdocs)
        project.pluginManager.apply(PluginIds.Kotlin.dokka)

        project.configure<MkdocsExtension>() {
            strict = false
            sourcesDir = "${project.rootProject.rootDir}/build/.docs"
            buildDir = "${project.rootProject.rootDir}/build/docs"
            publish.docPath = "_site"
        }

        project.allprojects { allproject ->
            allproject.afterEvaluate {
                if (it.hasKotlinGradlePlugin) {
                    it.pluginManager.apply(DokkaPlugin::class)
                    it.tasks.withType<DokkaTaskPartial>() {
                        dokkaSourceSets.configureEach { sourceSetBuilder ->
                            val includes: List<String> = buildList {
                                if (allproject.projectDir.resolve("MODULE.md").exists()) {
                                    add("MODULE.md")
                                }
                                if (allproject.projectDir.resolve("README.md").exists()) {
                                    add("README.md")
                                }
                            }

                            if (includes.isNotEmpty()) sourceSetBuilder.includes.from(includes)
                        }
                    }
                }
            }
        }

        val siteState = project.hubdleState.config.documentation.site

        val preBuildDocsTask =
            PrebuildSiteTask.register(project) {
                projectsInfo.set(project.projectsInfo)
                allTests.set(siteState.reports.allTests)
                codeAnalysis.set(siteState.reports.codeAnalysis)
                codeCoverage.set(siteState.reports.codeCoverage)
                codeQuality.set(siteState.reports.codeQuality)
            }

        BuildSiteTask.register(project, preBuildDocsTask)
    }
}

private val Project.hasKotlinGradlePlugin: Boolean
    get() = plugins.asSequence().mapNotNull { (it as? KotlinBasePluginWrapper) }.count() > 0
