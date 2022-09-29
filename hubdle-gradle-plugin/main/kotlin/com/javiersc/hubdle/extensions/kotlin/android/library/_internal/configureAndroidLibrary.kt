package com.javiersc.hubdle.extensions.kotlin.android.library._internal

import com.android.build.gradle.LibraryExtension
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.HubdleState
import com.javiersc.hubdle.extensions._internal.state.catalogDependency as catalogDep
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.explicit.api._internal.configureExplicitApi
import com.javiersc.hubdle.extensions.dependencies._internal.constants.COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.IO_KOTEST_KOTEST_ASSERTIONS_CORE_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_ANDROID_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_CORE_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_TEST_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_CORE_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_JSON_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_MODULE
import com.javiersc.hubdle.extensions.kotlin._internal.configJvmTarget
import com.javiersc.hubdle.extensions.kotlin.android._internal.calculateAndroidNamespace
import com.javiersc.hubdle.extensions.kotlin.android._internal.configureAndroidBuildFeatures
import com.javiersc.hubdle.extensions.options.configDefaultAndroidSourceSets
import com.javiersc.hubdle.extensions.options.configureJavaJarsForAndroidPublishing
import com.javiersc.hubdle.extensions.options.configureMavenPublication
import com.javiersc.hubdle.extensions.options.configurePublishingExtension
import com.javiersc.hubdle.extensions.options.configureSigningForPublishing
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

internal fun configureAndroidLibrary(project: Project) {
    val androidState = project.hubdleState.kotlin.android
    if (androidState.library.isEnabled) {
        project.pluginManager.apply(PluginIds.Android.kotlin)
        project.pluginManager.apply(PluginIds.Android.library)

        project.configureExplicitApi()
        project.configJvmTarget()

        project.the<KotlinProjectExtension>().configureAndroidDependencies()

        project.configure<LibraryExtension> {
            compileSdk = androidState.compileSdk
            defaultConfig.minSdk = androidState.minSdk
            namespace = project.calculateAndroidNamespace(androidState.namespace)

            sourceSets.all { set -> set.configDefaultAndroidSourceSets() }
            project.configureAndroidBuildFeatures(this)
        }

        if (project.hubdleState.config.publishing.isEnabled) {
            project.pluginManager.apply(PluginIds.Publishing.mavenPublish)
            project.configurePublishingExtension()
            project.configureMavenPublication("release")
            project.configureJavaJarsForAndroidPublishing()
            project.configureSigningForPublishing()
        }
    }
}

internal val Project.androidLibraryFeatures: HubdleState.Kotlin.Android.Library.Features
    get() = hubdleState.kotlin.android.library.features

private val KotlinDependencyHandler.androidLibraryFeatures:
    HubdleState.Kotlin.Android.Library.Features
    get() = project.androidLibraryFeatures

internal fun configureKotlinAndroidLibraryRawConfig(project: Project) {
    project.hubdleState.kotlin.android.library.rawConfig.android?.execute(project.the())
}

private fun KotlinProjectExtension.configureAndroidDependencies() {
    sourceSets.named("main") { set -> set.dependencies { configureMainDependencies() } }
    sourceSets.named("test") { set -> set.dependencies { configureTestDependencies() } }
}

private fun KotlinDependencyHandler.configureMainDependencies() {
    if (androidLibraryFeatures.coroutines) {
        implementation(catalogDep(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_ANDROID_MODULE))
        implementation(catalogDep(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_CORE_MODULE))
    }
    if (androidLibraryFeatures.extendedStdlib) {
        implementation(catalogDep(COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE))
    }
    if (androidLibraryFeatures.serialization.isEnabled) {
        project.pluginManager.apply(PluginIds.Kotlin.serialization)
        implementation(catalogDep(ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_CORE_MODULE))
        if (androidLibraryFeatures.serialization.useJson) {
            implementation(catalogDep(ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_JSON_MODULE))
        }
    }
}

private fun KotlinDependencyHandler.configureTestDependencies() {
    implementation(catalogDep(ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_MODULE))

    if (androidLibraryFeatures.coroutines) {
        implementation(catalogDep(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_TEST_MODULE))
    }
    if (androidLibraryFeatures.extendedTesting) {
        implementation(catalogDep(IO_KOTEST_KOTEST_ASSERTIONS_CORE_MODULE))
    }
}
