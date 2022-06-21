package com.javiersc.hubdle.extensions._internal.state.kotlin.gradle

import com.gradle.publish.PluginBundleExtension
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions._internal.state.kotlin.configJvmTarget
import com.javiersc.hubdle.extensions._internal.state.kotlin.configureExplicitApi
import com.javiersc.hubdle.extensions.configureDefaultJavaSourceSets
import com.javiersc.hubdle.extensions.configureDefaultKotlinSourceSets
import com.javiersc.hubdle.extensions.configureJavaJarsForPublishing
import com.javiersc.hubdle.extensions.configureMavenPublication
import com.javiersc.hubdle.extensions.configurePublishingExtension
import com.javiersc.hubdle.extensions.configureSigningForPublishing
import com.javiersc.hubdle.extensions.kotlin.jvm.javaExtension
import com.javiersc.hubdle.extensions.kotlin.kotlinExtension
import com.javiersc.hubdle.properties.PropertyKey
import com.javiersc.hubdle.properties.getProperty
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun configureGradlePlugin(project: Project) {
    if (hubdleState.kotlin.gradle.plugin.isEnabled) {
        project.pluginManager.apply(PluginIds.Gradle.javaGradlePlugin)
        project.pluginManager.apply(PluginIds.Kotlin.jvm)

        project.configureExplicitApi()
        project.configJvmTarget()
        project.javaExtension.configureDefaultJavaSourceSets()
        project.kotlinExtension.configureDefaultKotlinSourceSets()

        if (hubdleState.kotlin.isPublishingEnabled) {
            project.pluginManager.apply(PluginIds.Publishing.mavenPublish)
            project.pluginManager.apply(PluginIds.Publishing.signing)
            project.pluginManager.apply(PluginIds.Publishing.gradlePluginPublish)
            project.configureJavaJarsForPublishing()
            project.configurePublishingExtension()
            project.configureMavenPublication("java")
            project.configureSigningForPublishing()
            project.configure<PluginBundleExtension> {
                website = project.getProperty(PropertyKey.POM.url)
                vcsUrl = project.getProperty(PropertyKey.POM.scmUrl)
            }
        }
    }
}
