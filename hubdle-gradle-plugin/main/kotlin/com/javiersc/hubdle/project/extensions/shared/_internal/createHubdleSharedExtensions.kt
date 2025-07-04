package com.javiersc.hubdle.project.extensions.shared._internal

import com.javiersc.hubdle.project.extensions._internal.HubdleState
import com.javiersc.hubdle.project.extensions.gradle.HubdleGradleExtension
import com.javiersc.hubdle.project.extensions.gradle.HubdleGradlePluginExtension
import com.javiersc.hubdle.project.extensions.gradle.HubdleGradleVersionCatalogExtension
import com.javiersc.hubdle.project.extensions.shared.features.HubdleIntellijFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.HubdleJavaApplicationFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.HubdleJavaVersionFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.HubdleJvmToolchainFeatureExtension
import com.javiersc.hubdle.project.extensions.shared.features.intellij.HubdleIntellijPluginFeatureExtension

internal fun HubdleState.createHubdleSharedExtensions() {
    createExtension<HubdleGradleExtension> {
        createExtension<HubdleGradlePluginExtension>()
        createExtension<HubdleGradleVersionCatalogExtension>()
    }
    createExtension<HubdleIntellijFeatureExtension> {
        createExtension<HubdleIntellijPluginFeatureExtension>()
    }
    createExtension<HubdleJavaApplicationFeatureExtension>()
    createExtension<HubdleJavaVersionFeatureExtension>()
    createExtension<HubdleJvmToolchainFeatureExtension>()
}
