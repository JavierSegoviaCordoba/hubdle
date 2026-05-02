@file:Suppress("UnstableApiUsage")

package com.javiersc.hubdle.declarative

import hubdle.platform.HubdleDefinition
import org.gradle.features.binding.BuildModel
import org.gradle.features.binding.Definition

internal interface MainHubdleDefinition : Definition<BuildModel.None>, HubdleDefinition {
    override val featureName: String
        get() = "hubdle"
}
