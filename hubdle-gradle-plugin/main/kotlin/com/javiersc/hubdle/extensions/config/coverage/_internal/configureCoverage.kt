package com.javiersc.hubdle.extensions.config.coverage._internal

import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

internal fun configureCoverage(project: Project) {
    if (project.hubdleState.config.coverage.isEnabled) {
        check(project.isRootProject) {
            "Hubdle `coverage()` must be only configured in the root project"
        }
        project.pluginManager.apply(PluginIds.Kotlin.kover)
    }
}

internal fun configureKotlinCoverageRawConfig(project: Project) {
    project.hubdleState.config.coverage.rawConfig.kover?.execute(project.the())
}
