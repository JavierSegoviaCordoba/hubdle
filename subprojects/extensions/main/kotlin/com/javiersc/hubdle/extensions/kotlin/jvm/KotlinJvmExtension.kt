package com.javiersc.hubdle.extensions.kotlin.jvm

import com.javiersc.hubdle.extensions.OriginalConfigOptions
import com.javiersc.hubdle.extensions.PublishingOptions
import com.javiersc.hubdle.extensions.SourceDirectoriesOptions
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

public open class KotlinJvmExtension :
    KotlinJvmOptions,
    SourceDirectoriesOptions<KotlinSourceSet>,
    OriginalConfigOptions<KotlinJvmProjectExtension>,
    PublishingOptions {

    override fun Project.publishing() {
        pluginManager.apply(PluginIds.Publishing.mavenPublish)
        pluginManager.apply(PluginIds.Publishing.signing)

        hubdleState.kotlin.isPublishingEnabled = true
    }

    override var target: Int = KotlinJvmOptions.DefaultJvmTarget

    override val Project.sourceSets: NamedDomainObjectContainer<KotlinSourceSet>
        get() = kotlinJvmExtension.sourceSets

    public fun Project.main(action: Action<KotlinSourceSet> = Action {}) {
        kotlinJvmExtension.sourceSets.named("main", action::execute)
    }

    public fun Project.test(action: Action<KotlinSourceSet> = Action {}) {
        kotlinJvmExtension.sourceSets.named("test", action::execute)
    }

    override fun Project.originalConfig(action: Action<KotlinJvmProjectExtension>) {
        action.execute(kotlinJvmExtension)
    }
}

internal val Project.javaExtension: JavaPluginExtension
    get() = project.extensions.getByType()

internal val Project.kotlinJvmExtension: KotlinJvmProjectExtension
    get() = project.extensions.getByType()
