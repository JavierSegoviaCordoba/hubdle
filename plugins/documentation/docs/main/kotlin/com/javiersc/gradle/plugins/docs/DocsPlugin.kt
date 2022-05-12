@file:OptIn(ExperimentalStdlibApi::class)

package com.javiersc.gradle.plugins.docs

import com.javiersc.gradle.plugins.docs.internal.hasKotlinGradlePlugin
import com.javiersc.gradle.plugins.docs.tasks.BuildDocsTask
import com.javiersc.gradle.plugins.docs.tasks.PreBuildDocsTask
import com.javiersc.gradle.plugins.docs.tasks.projectsInfo
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.ide.idea.model.IdeaModel
import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.gradle.ext.settings
import org.jetbrains.gradle.ext.taskTriggers
import ru.vyarus.gradle.plugin.mkdocs.MkdocsExtension

abstract class DocsPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        check(target == target.rootProject) { "`docs` plugin must be applied in the root project" }

        target.pluginManager.apply("ru.vyarus.mkdocs")
        target.pluginManager.apply("org.jetbrains.dokka")
        target.pluginManager.apply("org.jetbrains.gradle.plugin.idea-ext")

        DocsExtension.createExtension(target)

        target.extensions.findByType<MkdocsExtension>()?.apply {
            strict = false
            sourcesDir = "${target.rootProject.rootDir}/build/.docs"
            buildDir = "${target.rootProject.rootDir}/build/docs"
            publish.docPath = "_site"
        }

        target.allprojects { project ->
            project.afterEvaluate {
                if (it.hasKotlinGradlePlugin) {
                    it.pluginManager.apply(DokkaPlugin::class)
                    it.tasks.withType<DokkaTaskPartial>() {
                        dokkaSourceSets.configureEach { sourceSetBuilder ->
                            sourceSetBuilder.includes.from(listOf("MODULE.md"))
                        }
                    }
                }
            }
        }

        val preBuildDocsTask =
            PreBuildDocsTask.register(target) {
                with(target.docsExtension) {
                    projectsInfo.set(target.projectsInfo)
                    allTests.set(navigation.get().reports.get().allTests.get())
                    codeAnalysis.set(navigation.get().reports.get().codeAnalysis.get())
                    codeCoverage.set(navigation.get().reports.get().codeCoverage.get())
                    codeQuality.set(navigation.get().reports.get().codeQuality.get())
                }
            }

        target.extensions.findByType<IdeaModel>()?.project?.settings {
            taskTriggers { beforeBuild(preBuildDocsTask) }
        }

        BuildDocsTask.register(target, preBuildDocsTask)
    }
}
