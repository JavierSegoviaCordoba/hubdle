@file:Suppress("UnstableApiUsage")

package com.javiersc.hubdle.declarative

import hubdle.declarative.platform.HubdleServices
import org.gradle.features.binding.BuildModel
import org.gradle.features.binding.ProjectFeatureApplicationContext
import org.gradle.features.binding.ProjectTypeApplyAction

internal abstract class HubdleApplyAction :
    ProjectTypeApplyAction<HubdleDefinition, BuildModel.None>, HubdleServices {

    override fun apply(
        context: ProjectFeatureApplicationContext,
        definition: HubdleDefinition,
        buildModel: BuildModel.None,
    ) = definition.run {
        val isEnabled: Boolean = enabled.orElse(true).get()

        if (isEnabled) {
            logHubdleStatus()
        }
    }

    private fun HubdleDefinition.logHubdleStatus() {
        logLifecycle { "Hubdle enabled on ${project.path}" }
    }

    companion object {
        const val NAME = "hubdle"
    }
}
