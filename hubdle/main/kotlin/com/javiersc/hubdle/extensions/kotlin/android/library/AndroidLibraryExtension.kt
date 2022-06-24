package com.javiersc.hubdle.extensions.kotlin.android.library

import com.android.build.api.dsl.AndroidSourceSet
import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.MainAndTestKotlinSourceSetsOptions
import com.javiersc.hubdle.extensions.kotlin.android.AndroidOptions
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmOptions
import com.javiersc.hubdle.extensions.options.FeaturesOptions
import com.javiersc.hubdle.extensions.options.PublishingOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.the

@HubdleDslMarker
public open class AndroidLibraryExtension
@Inject
constructor(
    objects: ObjectFactory,
) :
    FeaturesOptions<AndroidLibraryExtension.FeaturesExtension>,
    AndroidOptions,
    RawConfigOptions<LibraryExtension>,
    MainAndTestKotlinSourceSetsOptions<AndroidSourceSet> {

    override val features: FeaturesExtension = objects.newInstance()

    override var compileSdk: Int = AndroidOptions.DefaultCompileSdk

    override var minSdk: Int = AndroidOptions.DefaultMinSdk

    override var target: Int = KotlinJvmOptions.DefaultJvmTarget

    override val Project.sourceSets: NamedDomainObjectContainer<out AndroidSourceSet>
        get() = the<LibraryExtension>().sourceSets

    override fun Project.main(action: Action<AndroidSourceSet>) {
        the<LibraryExtension>().sourceSets.named("main", action::execute)
    }

    override fun Project.test(action: Action<AndroidSourceSet>) {
        the<LibraryExtension>().sourceSets.named("test", action::execute)
    }

    override fun Project.rawConfig(action: Action<LibraryExtension>) {
        project.pluginManager.apply(PluginIds.Android.library)
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
