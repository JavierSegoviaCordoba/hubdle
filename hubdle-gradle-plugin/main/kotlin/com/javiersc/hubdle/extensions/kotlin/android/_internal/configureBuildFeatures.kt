package com.javiersc.hubdle.extensions.kotlin.android._internal

import com.android.build.api.dsl.BuildFeatures as AndroidBuildFeatures
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.DefaultConfig
import com.android.build.api.dsl.ProductFlavor
import com.javiersc.gradle.properties.extensions.getPropertyOrNull
import com.javiersc.hubdle.HubdleProperty.Android.BuildFeatures
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project

internal fun Project.configureAndroidBuildFeatures(androidCommonExtension: AndroidCommonExtension) {
    val kotlinState = hubdleState.kotlin
    if (kotlinState.android.isEnabled || kotlinState.multiplatform.android.isEnabled) {
        configureFeatures(androidCommonExtension)
    }
}

private fun Project.configureFeatures(androidCommonExtension: AndroidCommonExtension) {
    val feats = project.hubdleState.kotlin.android.buildFeatures
    androidCommonExtension.buildFeatures {
        aidl = feats.aidl ?: propOrNull(BuildFeatures.aidl) ?: trueIfApp()
        buildConfig = feats.buildConfig ?: propOrNull(BuildFeatures.buildConfig) ?: trueIfApp()
        compose = feats.compose ?: propOrNull(BuildFeatures.compose) ?: false
        renderScript = feats.renderScript ?: propOrNull(BuildFeatures.renderScript) ?: trueIfApp()
        resValues = feats.resValues ?: propOrNull(BuildFeatures.resValues) ?: trueIfApp()
        shaders = feats.shaders ?: propOrNull(BuildFeatures.shaders) ?: trueIfApp()
        viewBinding = feats.viewBinding ?: propOrNull(BuildFeatures.viewBinding) ?: false
    }
}

private fun Project.propOrNull(key: String): Boolean? = getPropertyOrNull(key)?.toBoolean()

private fun Project.trueIfApp(): Boolean = hubdleState.kotlin.android.application.isEnabled

internal typealias AndroidCommonExtension =
    CommonExtension<out AndroidBuildFeatures, out BuildType, out DefaultConfig, out ProductFlavor>
