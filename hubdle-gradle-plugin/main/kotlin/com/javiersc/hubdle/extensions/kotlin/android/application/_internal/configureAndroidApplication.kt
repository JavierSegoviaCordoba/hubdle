package com.javiersc.hubdle.extensions.kotlin.android.application._internal

import com.android.build.api.dsl.ApplicationExtension
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.HubdleState
import com.javiersc.hubdle.extensions._internal.state.catalogDependency as catalogDep
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.explicit.api._internal.configureExplicitApi
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ANDROIDX_ACTIVITY_ACTIVITY_COMPOSE_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.IO_KOTEST_KOTEST_ASSERTIONS_CORE_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_ANDROID_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_CORE_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_TEST_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_CORE_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_JSON_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_MODULE
import com.javiersc.hubdle.extensions.kotlin._internal.configJvmTarget
import com.javiersc.hubdle.extensions.kotlin.android._internal.configureAndroidBuildFeatures
import com.javiersc.hubdle.extensions.options.configDefaultAndroidSourceSets
import com.javiersc.hubdle.extensions.options.configureDefaultKotlinSourceSets
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

internal fun configureAndroidApplication(project: Project) {
    val androidState = project.hubdleState.kotlin.android
    if (androidState.application.isEnabled) {
        project.pluginManager.apply(PluginIds.Android.kotlin)
        project.pluginManager.apply(PluginIds.Android.application)

        project.configureExplicitApi()
        project.configJvmTarget()

        project.the<KotlinProjectExtension>().configureAndroidDependencies()
        project.the<KotlinProjectExtension>().configureDefaultKotlinSourceSets()

        project.configureAndroidApplicationComposeFeature()

        project.configure<ApplicationExtension> {
            defaultConfig.applicationId = androidState.application.applicationId
            defaultConfig.versionCode = androidState.application.versionCode
            defaultConfig.versionName = androidState.application.versionName
            compileSdk = androidState.compileSdk
            defaultConfig.minSdk = androidState.minSdk
            // TODO: namespace = project.calculateAndroidNamespace(androidState.namespace)

            sourceSets.all { set -> set.configDefaultAndroidSourceSets() }
            project.configureAndroidBuildFeatures(this)
        }
    }
}

internal val Project.androidApplicationFeatures: HubdleState.Kotlin.Android.Application.Features
    get() = hubdleState.kotlin.android.application.features

internal fun Project.configureAndroidApplicationComposeFeature() {
    if (androidApplicationFeatures.compose) {
        pluginManager.apply(PluginIds.Kotlin.compose)
        project.configure<ApplicationExtension> { defaultConfig { buildFeatures.compose = true } }
    }
}

internal fun configureKotlinAndroidApplicationRawConfig(project: Project) {
    project.hubdleState.kotlin.android.application.rawConfig.android?.execute(project.the())
}

private fun KotlinProjectExtension.configureAndroidDependencies() {
    sourceSets.named("main") { set -> set.dependencies { configureMainDependencies() } }
    sourceSets.named("test") { set -> set.dependencies { configureTestDependencies() } }
}

private fun KotlinDependencyHandler.configureMainDependencies() {
    with(project) {
        if (androidApplicationFeatures.compose) {
            implementation(catalogDep(ANDROIDX_ACTIVITY_ACTIVITY_COMPOSE_MODULE))
        }
        if (androidApplicationFeatures.coroutines) {
            implementation(catalogDep(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_ANDROID_MODULE))
            implementation(catalogDep(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_CORE_MODULE))
        }
        if (androidApplicationFeatures.extendedStdlib) {
            implementation(catalogDep(COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE))
        }
        if (androidApplicationFeatures.serialization.isEnabled) {
            project.pluginManager.apply(PluginIds.Kotlin.serialization)
            implementation(catalogDep(ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_CORE_MODULE))
            if (androidApplicationFeatures.serialization.useJson) {
                implementation(catalogDep(ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_JSON_MODULE))
            }
        }
    }
}

private fun KotlinDependencyHandler.configureTestDependencies() {
    with(project) {
        implementation(catalogDep(ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_MODULE))

        if (androidApplicationFeatures.coroutines) {
            implementation(catalogDep(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_TEST_MODULE))
        }
        if (androidApplicationFeatures.extendedTesting) {
            implementation(catalogDep(IO_KOTEST_KOTEST_ASSERTIONS_CORE_MODULE))
        }
    }
}
