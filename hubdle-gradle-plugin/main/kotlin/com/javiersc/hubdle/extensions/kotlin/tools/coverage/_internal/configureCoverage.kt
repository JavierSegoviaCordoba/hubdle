package com.javiersc.hubdle.extensions.kotlin.tools.coverage._internal

import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project

internal fun configureCoverage(project: Project) {
    if (project.hubdleState.kotlin.tools.coverage.isEnabled) {
        check(project.isRootProject) {
            "Hubdle `coverage()` must be only configured in the root project"
        }
        project.pluginManager.apply(PluginIds.Kotlin.kover)
    }
}
