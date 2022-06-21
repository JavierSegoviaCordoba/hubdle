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
    val format = hubdleState.kotlin.tools.format

    if (format.isEnabled) {
        project.pluginManager.apply(PluginIds.Format.spotless)

        project.tasks.register("formatCheck").configure {
            it.group = "verification"
            it.dependsOn("spotlessCheck")
            it.dependsOn("formatKotlinCheck")
        }

        project.tasks.register("formatApply").configure {
            it.group = "verification"
            it.dependsOn("spotlessApply")
            it.dependsOn("formatKotlinApply")
        }

        project.tasks.register("formatKotlinCheck").configure {
            it.group = "verification"
            it.dependsOn("spotlessKotlinCheck")
        }

        project.tasks.register("formatKotlinApply").configure {
            it.group = "verification"
            it.dependsOn("spotlessKotlinApply")
        }

        project.configure<SpotlessExtension> {
            kotlin { kotlinExtension ->
                kotlinExtension.target(format.includes)
                kotlinExtension.targetExclude(format.excludes)
                kotlinExtension.ktfmt(format.ktfmtVersion).kotlinlangStyle()
            }
        }
    }
}
