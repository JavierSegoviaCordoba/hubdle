package com.javiersc.hubdle.extensions._internal.state.kotlin.tools

import com.diffplug.gradle.spotless.SpotlessExtension
import com.javiersc.gradle.extensions.isRootProject
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

// TODO: remove `allprojects` behavior when `code analysis` is migrated to per project configuration
internal fun configureFormat(project: Project) {
    if (project.isRootProject) project.allprojects { configureFormatForProject(it) }
    else configureFormatForProject(project)
}

private fun configureFormatForProject(project: Project) {
    val format = project.hubdleState.kotlin.tools.format

    if (format.isEnabled) {
        project.pluginManager.apply(PluginIds.Format.spotless)

        project.tasks.register("checkFormat").configure {
            it.group = "verification"
            it.dependsOn("spotlessCheck")
        }

        project.tasks.register("applyFormat").configure {
            it.group = "verification"
            it.dependsOn("spotlessApply")
        }

        project.tasks.register("checkKotlinFormat").configure {
            it.group = "verification"
            it.dependsOn("spotlessKotlinCheck")
        }

        project.tasks.register("applyKotlinFormat").configure {
            it.group = "verification"
            it.dependsOn("spotlessKotlinApply")
        }

        project.configure<SpotlessExtension> {
            kotlin {
                it.target(format.includes)
                it.targetExclude(format.excludes)
                it.ktfmt(format.ktfmtVersion).kotlinlangStyle()
            }
        }
    }
}
