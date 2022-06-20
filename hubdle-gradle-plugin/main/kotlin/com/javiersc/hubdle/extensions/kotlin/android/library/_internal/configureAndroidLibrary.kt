package com.javiersc.hubdle.extensions.kotlin.android.library._internal

import com.android.build.gradle.LibraryExtension
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.HubdleState
import com.javiersc.hubdle.extensions._internal.state.catalogImplementation
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.dependencies._internal.COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_ANDROID_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_CORE_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_TEST_MODULE
import com.javiersc.hubdle.extensions.kotlin._internal.configJvmTarget
import com.javiersc.hubdle.extensions.kotlin.tools.explicit.api._internal.configureExplicitApi
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
    if (project.hubdleState.kotlin.android.library.isEnabled) {
        project.pluginManager.apply(PluginIds.Kotlin.jvm)
        project.pluginManager.apply(PluginIds.Android.library)

        project.configureExplicitApi()
        project.configJvmTarget()

        project.the<KotlinProjectExtension>().configureAndroidDependencies()

        project.configure<LibraryExtension>() {
            compileSdk = project.hubdleState.kotlin.android.compileSdk
            defaultConfig.minSdk = project.hubdleState.kotlin.android.minSdk

            sourceSets.all { it.configDefaultAndroidSourceSets() }
        }

        if (project.hubdleState.kotlin.isPublishingEnabled) {
            project.pluginManager.apply(PluginIds.Publishing.mavenPublish)
            project.pluginManager.apply(PluginIds.Publishing.signing)
            project.configurePublishingExtension()
            project.configureMavenPublication("release")
            project.configureJavaJarsForAndroidPublishing()
            project.configureSigningForPublishing()
        }
    }
}

private fun KotlinProjectExtension.configureAndroidDependencies() {
    sourceSets.named("main") { it.dependencies { configureMainDependencies() } }
    sourceSets.named("test") { it.dependencies { configureTestDependencies() } }
}

private val KotlinDependencyHandler.androidFeatures: HubdleState.Kotlin.Android.Features
    get() = project.hubdleState.kotlin.android.features

private fun KotlinDependencyHandler.configureMainDependencies() {
    if (androidFeatures.coroutines) {
        catalogImplementation(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_ANDROID_MODULE)
        catalogImplementation(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_CORE_MODULE)
    }
    if (androidFeatures.javierScStdlib) {
        catalogImplementation(COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE)
    }
}

private fun KotlinDependencyHandler.configureTestDependencies() {
    if (androidFeatures.coroutines) {
        catalogImplementation(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_TEST_MODULE)
    }
}
