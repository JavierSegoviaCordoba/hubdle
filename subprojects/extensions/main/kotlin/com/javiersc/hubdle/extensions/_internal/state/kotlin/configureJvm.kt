package com.javiersc.hubdle.extensions._internal.state.kotlin

import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.configureDefaultJavaSourceSets
import com.javiersc.hubdle.extensions.configureDefaultKotlinSourceSets
import com.javiersc.hubdle.extensions.configureJavaJarsForPublishing
import com.javiersc.hubdle.extensions.configureMavenPublication
import com.javiersc.hubdle.extensions.configurePublishingExtension
import com.javiersc.hubdle.extensions.configureSigningForPublishing
import com.javiersc.hubdle.extensions.kotlin.jvm.javaExtension
import com.javiersc.hubdle.extensions.kotlin.kotlinExtension
import org.gradle.api.Project

internal fun configureJvm(project: Project) {
    if (hubdleState.kotlin.jvm.isEnabled) {
        project.pluginManager.apply(PluginIds.Kotlin.jvm)

        project.configureExplicitApi()
        project.configJvmTarget()
        project.javaExtension.configureDefaultJavaSourceSets()
        project.kotlinExtension.configureDefaultKotlinSourceSets()

        if (hubdleState.kotlin.isPublishingEnabled) {
            project.pluginManager.apply(PluginIds.Publishing.mavenPublish)
            project.pluginManager.apply(PluginIds.Publishing.signing)
            project.configurePublishingExtension()
            project.configureMavenPublication("java")
            project.configureJavaJarsForPublishing()
            project.configureSigningForPublishing()
        }
    }
}
