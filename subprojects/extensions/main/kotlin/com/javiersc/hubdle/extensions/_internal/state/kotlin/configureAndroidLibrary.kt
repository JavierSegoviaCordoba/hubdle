package com.javiersc.hubdle.extensions._internal.state.kotlin

import com.android.build.gradle.LibraryExtension
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.configDefaultAndroidSourceSets
import com.javiersc.hubdle.extensions.configureJavaJarsForAndroidPublishing
import com.javiersc.hubdle.extensions.configureMavenPublication
import com.javiersc.hubdle.extensions.configurePublishingExtension
import com.javiersc.hubdle.extensions.configureSigningForPublishing
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

internal fun configureAndroidLibrary(project: Project) {
    if (hubdleState.kotlin.android.library.isEnabled) {
        project.pluginManager.apply(PluginIds.Android.library)

        project.configJvmTarget()

        project.the<LibraryExtension>().apply {
            compileSdk = hubdleState.kotlin.android.compileSdk
            defaultConfig.minSdk = hubdleState.kotlin.android.minSdk

            sourceSets.all { it.configDefaultAndroidSourceSets() }
        }

        if (hubdleState.kotlin.isPublishingEnabled) {
            project.pluginManager.apply(PluginIds.Publishing.mavenPublish)
            project.pluginManager.apply(PluginIds.Publishing.signing)
            project.configurePublishingExtension()
            project.configureMavenPublication("release")
            project.configureJavaJarsForAndroidPublishing()
            project.configureSigningForPublishing()
        }
    }
}
