package com.javiersc.hubdle.extensions._internal.state.kotlin

import com.android.build.gradle.LibraryExtension
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions._internal.state.kotlin.tools.configureBinaryCompatibilityValidator
import com.javiersc.hubdle.extensions._internal.state.kotlin.tools.configureExplicitApi
import com.javiersc.hubdle.extensions.options.configDefaultAndroidSourceSets
import com.javiersc.hubdle.extensions.options.configureJavaJarsForAndroidPublishing
import com.javiersc.hubdle.extensions.options.configureMavenPublication
import com.javiersc.hubdle.extensions.options.configurePublishingExtension
import com.javiersc.hubdle.extensions.options.configureSigningForPublishing
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun configureAndroidLibrary(project: Project) {
    if (project.hubdleState.kotlin.android.library.isEnabled) {
        project.pluginManager.apply(PluginIds.Kotlin.jvm)
        project.pluginManager.apply(PluginIds.Android.library)

        project.configureBinaryCompatibilityValidator()
        project.configureExplicitApi()
        project.configJvmTarget()

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
