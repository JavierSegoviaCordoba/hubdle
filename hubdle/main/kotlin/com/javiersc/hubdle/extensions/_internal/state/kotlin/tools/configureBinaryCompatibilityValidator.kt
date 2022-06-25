package com.javiersc.hubdle.extensions._internal.state.kotlin.tools

import com.javiersc.gradle.extensions.maybeRegisterLazily
import com.javiersc.gradle.extensions.namedLazily
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.api.Task

internal fun Project.configureBinaryCompatibilityValidator() {
    if (hubdleState.kotlin.tools.binaryCompatibilityValidator) {
        pluginManager.apply(PluginIds.Kotlin.binaryCompatibilityValidator)
        allprojects { allProject ->
            val apiCheckTask = allProject.tasks.namedLazily<Task>("apiCheck")
            val checkApiTask = allProject.tasks.maybeRegisterLazily<Task>("checkApi")
            checkApiTask.configureEach { task -> task.dependsOn(apiCheckTask) }
        }
    }
}
