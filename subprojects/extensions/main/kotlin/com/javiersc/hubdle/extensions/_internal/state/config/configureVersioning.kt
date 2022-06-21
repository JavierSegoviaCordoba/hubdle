package com.javiersc.hubdle.extensions._internal.state.config

import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.semver.gradle.plugin.SemverExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

internal fun configureVersioning(project: Project) {
    if (hubdleState.config.versioning.isEnabled) {
        project.pluginManager.apply(PluginIds.JavierSC.semver)

        project.the<SemverExtension>().tagPrefix.set(hubdleState.config.versioning.tagPrefix)
    }
}
