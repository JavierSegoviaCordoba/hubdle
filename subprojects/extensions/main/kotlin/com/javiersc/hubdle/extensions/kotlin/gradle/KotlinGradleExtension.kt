package com.javiersc.hubdle.extensions.kotlin.gradle

import com.javiersc.hubdle.extensions.GradleDependenciesOptions
import com.javiersc.hubdle.extensions.OriginalConfigOptions
import com.javiersc.hubdle.extensions.PublishingOptions
import com.javiersc.hubdle.extensions.SourceDirectoriesOptions
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmOptions
import com.javiersc.hubdle.extensions.kotlin.jvm.kotlinJvmExtension
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

public open class KotlinGradleExtension :
    KotlinJvmOptions,
    SourceDirectoriesOptions<KotlinSourceSet>,
    OriginalConfigOptions<KotlinJvmProjectExtension>,
    GradleDependenciesOptions,
    PublishingOptions {

    override fun Project.publishing() {
        pluginManager.apply(PluginIds.Publishing.mavenPublish)
        pluginManager.apply(PluginIds.Publishing.signing)
        pluginManager.apply(PluginIds.Publishing.gradlePluginPublish)

        hubdleState.kotlin.isPublishingEnabled = true
    }

    override var target: Int = KotlinJvmOptions.DefaultGradleJvmTarget

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
