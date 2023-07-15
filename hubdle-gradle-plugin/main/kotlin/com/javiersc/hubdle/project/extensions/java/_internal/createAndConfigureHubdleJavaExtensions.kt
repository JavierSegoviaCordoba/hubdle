package com.javiersc.hubdle.project.extensions.java._internal

import com.javiersc.hubdle.project.extensions._internal.HubdleState
import com.javiersc.hubdle.project.extensions.java.HubdleJavaExtension
import com.javiersc.hubdle.project.extensions.java.features.HubdleJavaFeaturesExtension

internal fun HubdleState.createHubdleJavaExtensions() {
    createExtension<HubdleJavaExtension> { createExtension<HubdleJavaFeaturesExtension>() }
}
