package com.javiersc.hubdle.extensions.config.coverage._internal

import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import kotlinx.kover.api.KoverMergedConfig
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

internal fun configureCoverage(project: Project) {
    if (project.hubdleState.config.coverage.isEnabled) {
        check(project.isRootProject) {
            "Hubdle `coverage()` must be only configured in the root project"
        }
        project.allprojects {
            pluginManager.apply(PluginIds.Kotlin.kover)
            the<KoverMergedConfig>().enable()
        }
    }
}

internal fun configureKotlinCoverageRawConfig(project: Project) {
    project.hubdleState.config.coverage.rawConfig.kover?.execute(project.the())
}
