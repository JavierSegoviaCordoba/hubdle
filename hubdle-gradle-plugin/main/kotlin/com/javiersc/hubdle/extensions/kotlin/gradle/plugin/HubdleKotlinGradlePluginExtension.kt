package com.javiersc.hubdle.extensions.kotlin.gradle.plugin

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.gradle.plugin._internal.gradlePluginFeatures
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmOptions
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.FeaturesOptions
import com.javiersc.hubdle.extensions.options.GradleDependenciesOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import com.javiersc.hubdle.extensions.options.SourceDirectoriesOptions
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.the
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@HubdleDslMarker
public open class HubdleKotlinGradlePluginExtension
@Inject
constructor(
    objects: ObjectFactory,
) :
    EnableableOptions,
    FeaturesOptions<HubdleKotlinGradlePluginExtension.FeaturesExtension>,
    KotlinJvmOptions,
    SourceDirectoriesOptions<KotlinSourceSet>,
    RawConfigOptions<HubdleKotlinGradlePluginExtension.RawConfigExtension>,
    GradleDependenciesOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.gradle.plugin.isEnabled
        set(value) = hubdleState.kotlin.gradle.plugin.run { isEnabled = value }

    override val features: FeaturesExtension = objects.newInstance()

    @HubdleDslMarker
    override fun features(action: Action<FeaturesExtension>): Unit = super.features(action)

    override val Project.sourceSets: NamedDomainObjectContainer<KotlinSourceSet>
        get() = the<KotlinJvmProjectExtension>().sourceSets

    @HubdleDslMarker
    public fun Project.tags(vararg tags: String) {
        hubdleState.kotlin.gradle.plugin.tags += tags
    }

    @HubdleDslMarker
    public fun Project.gradlePlugin(action: Action<GradlePluginDevelopmentExtension>) {
        hubdleState.kotlin.gradle.plugin.gradlePlugin = action
    }

    @HubdleDslMarker
    public fun Project.main(action: Action<KotlinSourceSet> = Action {}) {
        the<KotlinJvmProjectExtension>().sourceSets.named("main", action::execute)
    }

    @HubdleDslMarker
    public fun Project.test(action: Action<KotlinSourceSet> = Action {}) {
        the<KotlinJvmProjectExtension>().sourceSets.named("test", action::execute)
    }

    @HubdleDslMarker
    public fun Project.pluginUnderTestDependencies(
        vararg pluginUnderTestDependencies: Provider<MinimalExternalModuleDependency>,
    ) {
        hubdleState.kotlin.gradle.plugin.pluginUnderTestDependencies += pluginUnderTestDependencies
    }

    override val rawConfig: RawConfigExtension = objects.newInstance()

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        action.execute(rawConfig)
    }

    @HubdleDslMarker
    public open class FeaturesExtension {

        @HubdleDslMarker
        public fun Project.extendedGradle(enabled: Boolean = true) {
            gradlePluginFeatures.extendedGradle = enabled
        }

        @HubdleDslMarker
        public fun Project.extendedStdlib(enabled: Boolean = true) {
            gradlePluginFeatures.extendedStdlib = enabled
        }

        @HubdleDslMarker
        public fun Project.extendedTesting(enabled: Boolean = true) {
            gradlePluginFeatures.extendedTesting = enabled
        }
    }

    @HubdleDslMarker
    public open class RawConfigExtension {

        public fun Project.kotlin(action: Action<KotlinJvmProjectExtension>) {
            hubdleState.kotlin.gradle.plugin.rawConfig.kotlin = action
        }

        public fun Project.gradlePlugin(action: Action<GradlePluginDevelopmentExtension>) {
            hubdleState.kotlin.gradle.plugin.rawConfig.gradlePlugin = action
        }
    }
}
