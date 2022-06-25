package com.javiersc.hubdle.extensions._internal.state.kotlin.tools

import com.javiersc.gradle.extensions.isRootProject
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project

internal fun configureCoverage(project: Project) {
    if (project.hubdleState.kotlin.tools.coverage.isEnabled) {
        check(project.isRootProject) {
            "Coverage must be used only in root project until new Kover design API is released"
        }
        project.pluginManager.apply(PluginIds.Kotlin.kover)
    }
}
