package com.javiersc.hubdle.extensions.kotlin.intellij

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.MainAndTestKotlinSourceSetsOptions
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmOptions
import com.javiersc.hubdle.extensions.kotlin.jvm._internal.jvmFeatures
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.FeaturesOptions
import com.javiersc.hubdle.extensions.options.GradleDependenciesOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import com.javiersc.hubdle.extensions.options.SourceDirectoriesOptions
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.the
import org.jetbrains.intellij.IntelliJPluginExtension
import org.jetbrains.intellij.tasks.PatchPluginXmlTask
import org.jetbrains.intellij.tasks.PublishPluginTask
import org.jetbrains.intellij.tasks.SignPluginTask
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@HubdleDslMarker
public open class IntellijExtension
@Inject
constructor(
    objects: ObjectFactory,
) :
    EnableableOptions,
    FeaturesOptions<IntellijExtension.FeaturesExtension>,
    KotlinJvmOptions,
    SourceDirectoriesOptions<KotlinSourceSet>,
    RawConfigOptions<IntellijExtension.RawConfigExtension>,
    MainAndTestKotlinSourceSetsOptions<KotlinSourceSet>,
    GradleDependenciesOptions {

    override var Project.isEnabled: Boolean
        get() = hubdleState.kotlin.intellij.isEnabled
        set(value) = hubdleState.kotlin.intellij.run { isEnabled = value }

    override val features: FeaturesExtension = objects.newInstance()

    @HubdleDslMarker
    override fun features(action: Action<FeaturesExtension>): Unit = super.features(action)

    override val Project.sourceSets: NamedDomainObjectContainer<KotlinSourceSet>
        get() = the<KotlinJvmProjectExtension>().sourceSets

    @HubdleDslMarker
    public fun Project.intellij(action: Action<IntelliJPluginExtension> = Action {}) {
        hubdleState.kotlin.intellij.intellij = action
    }

    @HubdleDslMarker
    public fun Project.patchPluginXml(action: Action<PatchPluginXmlTask> = Action {}) {
        hubdleState.kotlin.intellij.patchPluginXml = action
    }

    @HubdleDslMarker
    public fun Project.publishPlugin(action: Action<PublishPluginTask> = Action {}) {
        hubdleState.kotlin.intellij.publishPlugin = action
    }

    @HubdleDslMarker
    public fun Project.signPlugin(action: Action<SignPluginTask> = Action {}) {
        hubdleState.kotlin.intellij.signPlugin = action
    }

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
