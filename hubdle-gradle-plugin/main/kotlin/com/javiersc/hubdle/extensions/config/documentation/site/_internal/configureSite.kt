package com.javiersc.hubdle.extensions.config.documentation.site._internal

import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config._internal.hasKotlinGradlePlugin
import com.javiersc.hubdle.extensions.config.documentation.site.BuildSiteTask
import com.javiersc.hubdle.extensions.config.documentation.site.PrebuildSiteTask
import com.javiersc.hubdle.extensions.config.documentation.site.projectsInfo
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import ru.vyarus.gradle.plugin.mkdocs.MkdocsExtension

internal fun configureSite(project: Project) {
    if (project.hubdleState.config.documentation.site.isEnabled) {
        check(project.isRootProject) {
            """
                |`site` can be only used in the root project, and it is being applied in:
                |  - `${project.path}
            """
                .trimMargin()
        }

        project.pluginManager.apply(PluginIds.Kotlin.dokka)
        project.pluginManager.apply(PluginIds.Documentation.mkdocs)

        project.tasks.withType<DokkaMultiModuleTask>().configureEach { task ->
            val excludedProjects =
                project.hubdleState.config.documentation.site.excludes.map(
                    ProjectDependency::getDependencyProject
                )
            task.removeChildTasks(excludedProjects)
        }

        project.configure<MkdocsExtension> {
            strict = false
            sourcesDir = "${project.rootProject.rootDir}/build/.docs"
            buildDir = "${project.rootProject.rootDir}/build/docs"
            publish.docPath = "_site"
        }

        project.allprojects { allproject ->
            allproject.afterEvaluate { allprojectAfterEvaluate ->
                if (allprojectAfterEvaluate.hasKotlinGradlePlugin) {
                    allprojectAfterEvaluate.pluginManager.apply(PluginIds.Kotlin.dokka)
                    allprojectAfterEvaluate.tasks.withType<DokkaTaskPartial> {
                        dokkaSourceSets.configureEach { set ->
                            val includes: List<String> = buildList {
                                val projectDir = allprojectAfterEvaluate.projectDir
                                if (projectDir.resolve("MODULE.md").exists()) {
                                    add("MODULE.md")
                                }
                                if (projectDir.resolve("README.md").exists()) {
                                    add("README.md")
                                }
                            }

                            if (includes.isNotEmpty()) set.includes.from(includes)
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

internal fun configureConfigDocumentationSiteRawConfig(project: Project) {
    project.hubdleState.config.documentation.site.rawConfig.mkdocs?.execute(project.the())
}
