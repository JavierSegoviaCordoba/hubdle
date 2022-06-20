package com.javiersc.hubdle.extensions.kotlin.tools.binary.compatibility.validator._internal

import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.api.Task

// TODO: change to per project when it is redesigned to support project isolation
internal fun configureBinaryCompatibilityValidator(project: Project) {
    if (project.hubdleState.kotlin.tools.binaryCompatibilityValidator.isEnabled) {
        check(project.isRootProject) {
            "`binaryCompatibilityValidator` must be applied only on root project"
        }
        project.pluginManager.apply(PluginIds.Kotlin.binaryCompatibilityValidator)
        project.allprojects { allProject ->
            val apiCheckTask = allProject.tasks.namedLazily<Task>("apiCheck")
            val checkApiTask = allProject.tasks.maybeRegisterLazily<Task>("checkApi")
            checkApiTask.configureEach { task -> task.dependsOn(apiCheckTask) }

            val apiDumpTask = allProject.tasks.namedLazily<Task>("apiDump")
            val dumpApiTask = allProject.tasks.maybeRegisterLazily<Task>("dumpApi")
            dumpApiTask.configureEach { task -> task.dependsOn(apiDumpTask) }
        }
    }
}
