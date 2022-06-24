package com.javiersc.hubdle.extensions._internal.state.kotlin.tools

import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project

internal fun Project.configureBinaryCompatibilityValidator() {
    if (hubdleState.kotlin.tools.binaryCompatibilityValidator) {
        pluginManager.apply(PluginIds.Kotlin.binaryCompatibilityValidator)
    }
}
