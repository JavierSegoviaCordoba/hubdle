package com.javiersc.hubdle.extensions.kotlin.jvm

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.MainAndTestKotlinSourceSetsOptions
import com.javiersc.hubdle.extensions.kotlin.jvm._internal.jvmFeatures
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.FeaturesOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import com.javiersc.hubdle.extensions.options.SourceDirectoriesOptions
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.JavaApplication
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@HubdleDslMarker
public open class HubdleKotlinJvmExtension
@Inject
constructor(
    objects: ObjectFactory,
) :
    EnableableOptions,
    FeaturesOptions<HubdleKotlinJvmExtension.FeaturesExtension>,
    KotlinJvmOptions,
    SourceDirectoriesOptions<KotlinSourceSet>,
    RawConfigOptions<HubdleKotlinJvmExtension.RawConfigExtension>,
    MainAndTestKotlinSourceSetsOptions<KotlinSourceSet> {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.jvm.isEnabled
        set(value) = hubdleState.kotlin.jvm.run { isEnabled = value }

    override val features: FeaturesExtension = objects.newInstance()

    @HubdleDslMarker
    override fun features(action: Action<FeaturesExtension>): Unit = super.features(action)

    @HubdleDslMarker
    public fun Project.application(action: Action<JavaApplication>) {
        pluginManager.apply(PluginIds.Gradle.application)
        hubdleState.kotlin.jvm.application = action
    }

    override val Project.sourceSets: NamedDomainObjectContainer<KotlinSourceSet>
        get() = the<KotlinJvmProjectExtension>().sourceSets

    @HubdleDslMarker
    override fun Project.main(action: Action<KotlinSourceSet>) {
        the<KotlinJvmProjectExtension>().sourceSets.named("main", action::execute)
    }

    @HubdleDslMarker
    override fun Project.test(action: Action<KotlinSourceSet>) {
        the<KotlinJvmProjectExtension>().sourceSets.named("test", action::execute)
    }

    override val rawConfig: RawConfigExtension = objects.newInstance()

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        action.execute(rawConfig)
    }

    @HubdleDslMarker
    public open class FeaturesExtension {

        @HubdleDslMarker
        public fun Project.coroutines(enabled: Boolean = true) {
            jvmFeatures.coroutines = enabled
        }

        @HubdleDslMarker
        public fun Project.extendedGradle(enabled: Boolean = true) {
            jvmFeatures.extendedGradle = enabled
        }

        @HubdleDslMarker
        public fun Project.extendedStdlib(enabled: Boolean = true) {
            jvmFeatures.extendedStdlib = enabled
        }

        @HubdleDslMarker
        public fun Project.extendedTesting(enabled: Boolean = true) {
            jvmFeatures.extendedTesting = enabled
        }

        @HubdleDslMarker
        public fun Project.serialization(enabled: Boolean = true, useJson: Boolean = true) {
            jvmFeatures.serialization.isEnabled = enabled
            jvmFeatures.serialization.useJson = useJson
        }
    }

    @HubdleDslMarker
    public open class RawConfigExtension {

        public fun Project.kotlin(action: Action<KotlinJvmProjectExtension>) {
            hubdleState.kotlin.jvm.rawConfig.kotlin = action
        }
    }
}
