package com.javiersc.hubdle.extensions.kotlin.jvm

import com.javiersc.hubdle.extensions.DependenciesOptions
import com.javiersc.hubdle.extensions.OriginalConfigOptions
import com.javiersc.hubdle.extensions.PublishingOptions
import com.javiersc.hubdle.extensions.SourceDirectoriesOptions
import com.javiersc.hubdle.extensions.configureJavaJarsForPublishing
import com.javiersc.hubdle.extensions.configureMavenPublication
import com.javiersc.hubdle.extensions.configurePublishingExtension
import com.javiersc.hubdle.extensions.configureSigningForPublishing
import com.javiersc.hubdle.extensions.internal.PluginIds
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies as dependenciesDsl
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

public open class KotlinJvmExtension :
    KotlinJvmOptions,
    SourceDirectoriesOptions<KotlinSourceSet>,
    DependenciesOptions,
    OriginalConfigOptions<KotlinJvmProjectExtension>,
    PublishingOptions {

    override fun Project.publishing() {
        pluginManager.apply(PluginIds.Publishing.mavenPublish)
        pluginManager.apply(PluginIds.Publishing.signing)
        configurePublishingExtension()
        configureMavenPublication("java")
        configureJavaJarsForPublishing()
        configureSigningForPublishing()
    }

    override var target: Int = KotlinJvmOptions.DefaultJvmTarget

    override val Project.sourceSets: NamedDomainObjectContainer<KotlinSourceSet>
        get() = kotlinJvmExtension.sourceSets

    override fun Project.dependencies(configuration: DependencyHandlerScope.() -> Unit) {
        dependenciesDsl { configuration(this) }
    }

    override fun Project.originalConfig(action: Action<KotlinJvmProjectExtension>) {
        action.execute(kotlinJvmExtension)
    }
}

internal val Project.javaExtension: JavaPluginExtension
    get() = project.extensions.getByType()

internal val Project.kotlinJvmExtension: KotlinJvmProjectExtension
    get() = project.extensions.getByType()
