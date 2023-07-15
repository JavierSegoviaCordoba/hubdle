package com.javiersc.hubdle.project.extensions.shared._internal

import com.javiersc.hubdle.project.extensions._internal.HubdleState
import com.javiersc.hubdle.project.extensions.shared.features.HubdleGradleFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.HubdleIntellijFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.HubdleJavaApplicationFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.HubdleJavaVersionFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.HubdleJvmToolchainFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.gradle.HubdleGradlePluginFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.gradle.HubdleGradleVersionCatalogFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.intellij.HubdleIntellijPluginFeatureExtension

internal fun HubdleState.createHubdleSharedExtensions() {
    createExtension<HubdleGradleFeatureExtension> {
        createExtension<HubdleGradlePluginFeatureExtension>()
        createExtension<HubdleGradleVersionCatalogFeatureExtension>()
    }
    createExtension<HubdleIntellijFeatureExtension> {
        createExtension<HubdleIntellijPluginFeatureExtension>()
    }
    createExtension<HubdleJavaApplicationFeatureExtension>()
    createExtension<HubdleJavaVersionFeatureExtension>()
    createExtension<HubdleJvmToolchainFeatureExtension>()
}
