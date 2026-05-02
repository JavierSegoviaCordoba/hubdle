@file:Suppress("UnstableApiUsage")

package com.javiersc.hubdle.declarative

import hubdle.declarative.platform.HubdleFeatureEnabled
import org.gradle.features.binding.BuildModel
import org.gradle.features.binding.Definition

internal interface HubdleDefinition : Definition<BuildModel.None>, HubdleFeatureEnabled
