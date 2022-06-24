package com.javiersc.hubdle.extensions._internal.state.kotlin.gradle

import com.gradle.publish.PluginBundleExtension
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions._internal.state.kotlin.configJvmTarget
import com.javiersc.hubdle.extensions._internal.state.kotlin.tools.configureBinaryCompatibilityValidator
import com.javiersc.hubdle.extensions._internal.state.kotlin.tools.configureExplicitApi
import com.javiersc.hubdle.extensions.options.configureDefaultJavaSourceSets
import com.javiersc.hubdle.extensions.options.configureDefaultKotlinSourceSets
import com.javiersc.hubdle.extensions.options.configureJavaJarsForPublishing
import com.javiersc.hubdle.extensions.options.configureMavenPublication
import com.javiersc.hubdle.extensions.options.configurePublishingExtension
import com.javiersc.hubdle.extensions.options.configureSigningForPublishing
import com.javiersc.hubdle.properties.PropertyKey
import com.javiersc.hubdle.properties.getProperty
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal fun configureGradlePlugin(project: Project) {
    if (project.hubdleState.kotlin.gradle.plugin.isEnabled) {
        project.pluginManager.apply(PluginIds.Gradle.javaGradlePlugin)
        project.pluginManager.apply(PluginIds.Kotlin.jvm)

        project.configureBinaryCompatibilityValidator()
        project.configureExplicitApi()
        project.configJvmTarget()
        project.the<JavaPluginExtension>().configureDefaultJavaSourceSets()
        project.the<KotlinProjectExtension>().configureDefaultKotlinSourceSets()

        if (project.hubdleState.kotlin.isPublishingEnabled) {
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
