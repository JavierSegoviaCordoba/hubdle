package com.javiersc.hubdle.extensions.kotlin.gradle.plugin

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmOptions
import com.javiersc.hubdle.extensions.options.FeaturesOptions
import com.javiersc.hubdle.extensions.options.GradleDependenciesOptions
import com.javiersc.hubdle.extensions.options.PublishingOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import com.javiersc.hubdle.extensions.options.SourceDirectoriesOptions
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.the
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@HubdleDslMarker
public open class GradlePluginExtension
@Inject
constructor(
    objects: ObjectFactory,
) :
    FeaturesOptions<GradlePluginExtension.FeaturesExtension>,
    KotlinJvmOptions,
    SourceDirectoriesOptions<KotlinSourceSet>,
    RawConfigOptions<GradlePluginExtension.KotlinJvmProjectAndGradleDevelopmentExtension>,
    GradleDependenciesOptions {

    override val features: FeaturesExtension = objects.newInstance()

    override var target: Int = KotlinJvmOptions.DefaultGradleJvmTarget

    override val Project.sourceSets: NamedDomainObjectContainer<KotlinSourceSet>
        get() = the<KotlinJvmProjectExtension>().sourceSets

    public fun Project.main(action: Action<KotlinSourceSet> = Action {}) {
        the<KotlinJvmProjectExtension>().sourceSets.named("main", action::execute)
    }

    public fun Project.test(action: Action<KotlinSourceSet> = Action {}) {
        the<KotlinJvmProjectExtension>().sourceSets.named("test", action::execute)
    }

    override fun Project.rawConfig(action: Action<KotlinJvmProjectAndGradleDevelopmentExtension>) {
        project.pluginManager.apply(PluginIds.Kotlin.jvm)
        project.pluginManager.apply(PluginIds.Gradle.javaGradlePlugin)
        action.execute(the())
    }

    @HubdleDslMarker
    public abstract class FeaturesExtension : PublishingOptions {

        override fun Project.publishing() {
            pluginManager.apply(PluginIds.Publishing.mavenPublish)
            pluginManager.apply(PluginIds.Publishing.signing)
            pluginManager.apply(PluginIds.Publishing.gradlePluginPublish)

            hubdleState.kotlin.isPublishingEnabled = true
        }
    }

    public class KotlinJvmProjectAndGradleDevelopmentExtension {

        public fun Project.kotlinJvmProject(action: Action<KotlinJvmProjectExtension>) {
            action.execute(the())
        }

        public fun Project.gradlePluginDevelopment(
            action: Action<GradlePluginDevelopmentExtension>
        ) {
            action.execute(the())
        }
    }
}
