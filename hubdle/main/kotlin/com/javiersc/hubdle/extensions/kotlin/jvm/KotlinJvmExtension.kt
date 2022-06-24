package com.javiersc.hubdle.extensions.kotlin.jvm

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.MainAndTestKotlinSourceSetsOptions
import com.javiersc.hubdle.extensions.options.FeaturesOptions
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
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@HubdleDslMarker
public open class KotlinJvmExtension
@Inject
constructor(
    objects: ObjectFactory,
) :
    FeaturesOptions<KotlinJvmExtension.FeaturesExtension>,
    KotlinJvmOptions,
    SourceDirectoriesOptions<KotlinSourceSet>,
    RawConfigOptions<KotlinJvmProjectExtension>,
    MainAndTestKotlinSourceSetsOptions<KotlinSourceSet> {

    override val features: FeaturesExtension = objects.newInstance()

    override var target: Int = KotlinJvmOptions.DefaultJvmTarget

    override val Project.sourceSets: NamedDomainObjectContainer<KotlinSourceSet>
        get() = the<KotlinJvmProjectExtension>().sourceSets

    override fun Project.main(action: Action<KotlinSourceSet>) {
        the<KotlinJvmProjectExtension>().sourceSets.named("main", action::execute)
    }

    override fun Project.test(action: Action<KotlinSourceSet>) {
        the<KotlinJvmProjectExtension>().sourceSets.named("test", action::execute)
    }

    override fun Project.rawConfig(action: Action<KotlinJvmProjectExtension>) {
        project.pluginManager.apply(PluginIds.Kotlin.jvm)
        action.execute(the())
    }

    @HubdleDslMarker
    public abstract class FeaturesExtension : PublishingOptions {

        override fun Project.publishing() {
            pluginManager.apply(PluginIds.Publishing.mavenPublish)
            pluginManager.apply(PluginIds.Publishing.signing)

            hubdleState.kotlin.isPublishingEnabled = true
        }
    }
}
