package com.javiersc.hubdle.extensions.config.versioning._internal

import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.semver.gradle.plugin.SemverExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

internal fun configureVersioning(project: Project) {
    val versioningState = project.hubdleState.config.versioning
    if (versioningState.isEnabled) {
        project.pluginManager.apply(PluginIds.JavierSC.semver)

        project.the<SemverExtension>().tagPrefix.set(versioningState.tagPrefix)
    }
}
