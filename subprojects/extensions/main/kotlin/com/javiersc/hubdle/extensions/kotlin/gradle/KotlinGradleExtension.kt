package com.javiersc.hubdle.extensions.kotlin.gradle

import com.gradle.publish.PluginBundleExtension
import com.javiersc.hubdle.extensions.DependenciesOptions
import com.javiersc.hubdle.extensions.OriginalConfigOptions
import com.javiersc.hubdle.extensions.PublishingOptions
import com.javiersc.hubdle.extensions.SourceDirectoriesOptions
import com.javiersc.hubdle.extensions.configureJavaJarsForPublishing
import com.javiersc.hubdle.extensions.configureMavenPublication
import com.javiersc.hubdle.extensions.configurePublishingExtension
import com.javiersc.hubdle.extensions.configureSigningForPublishing
import com.javiersc.hubdle.extensions.internal.Kotlin
import com.javiersc.hubdle.extensions.internal.PluginIds
import com.javiersc.hubdle.extensions.internal.extensionTracker
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmOptions
import com.javiersc.hubdle.extensions.kotlin.jvm.kotlinJvmExtension
import com.javiersc.hubdle.properties.PropertyKey
import com.javiersc.hubdle.properties.getProperty
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies as dependenciesDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

public open class KotlinGradleExtension :
    KotlinJvmOptions,
    SourceDirectoriesOptions<KotlinSourceSet>,
    DependenciesOptions,
    OriginalConfigOptions<KotlinJvmProjectExtension>,
    PublishingOptions {

    override fun Project.publishing() {
        extensionTracker.put(Kotlin.Publishing)
        pluginManager.apply(PluginIds.Publishing.mavenPublish)
        pluginManager.apply(PluginIds.Publishing.signing)
        pluginManager.apply(PluginIds.Publishing.gradlePluginPublish)
        configureJavaJarsForPublishing()
        configurePublishingExtension()
        configureMavenPublication("java")
        configureSigningForPublishing()
        configure<PluginBundleExtension> {
            website = getProperty(PropertyKey.POM.url)
            vcsUrl = property(PropertyKey.POM.scmUrl).toString()
        }
    }

    override var target: Int = KotlinJvmOptions.DefaultGradleJvmTarget

    override val Project.sourceSets: NamedDomainObjectContainer<KotlinSourceSet>
        get() = kotlinJvmExtension.sourceSets

    override fun Project.dependencies(configuration: DependencyHandlerScope.() -> Unit) {
        dependenciesDsl { configuration(this) }
    }

    override fun Project.originalConfig(action: Action<KotlinJvmProjectExtension>) {
        action.execute(kotlinJvmExtension)
    }
}
