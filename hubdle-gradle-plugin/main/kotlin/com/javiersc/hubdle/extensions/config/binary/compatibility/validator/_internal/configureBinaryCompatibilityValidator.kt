package com.javiersc.hubdle.extensions.config.binary.compatibility.validator._internal

import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.the

// TODO: change to per project when it is redesigned to support project isolation
internal fun configureBinaryCompatibilityValidator(project: Project) {
    if (project.hubdleState.config.binaryCompatibilityValidator.isEnabled) {
        check(project.isRootProject) {
            "`binaryCompatibilityValidator` must be applied only on root project"
        }
        project.pluginManager.apply(PluginIds.Kotlin.binaryCompatibilityValidator)
        project.allprojects {
            val apiCheckTask = tasks.namedLazily<Task>("apiCheck")
            val checkApiTask = tasks.maybeRegisterLazily<Task>("checkApi")
            checkApiTask.configureEach {
                group = "verification"
                dependsOn(apiCheckTask)
            }

            val apiDumpTask = tasks.namedLazily<Task>("apiDump")
            val dumpApiTask = tasks.maybeRegisterLazily<Task>("dumpApi")
            dumpApiTask.configureEach { dependsOn(apiDumpTask) }
        }
    }
}

internal fun configureConfigBinaryCompatibilityValidatorRawConfig(project: Project) {
    project.hubdleState.config.binaryCompatibilityValidator.rawConfig.apiValidation?.execute(
        project.the()
    )
}
