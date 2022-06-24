package com.javiersc.hubdle.extensions.kotlin.multiplatform

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmOptions
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformAndroidExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformCommonExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformJvmExtension
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
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension as KotlinProjectMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@HubdleDslMarker
public open class KotlinMultiplatformExtension
@Inject
constructor(
    objects: ObjectFactory,
) :
    FeaturesOptions<KotlinMultiplatformExtension.FeaturesExtension>,
    KotlinJvmOptions,
    SourceDirectoriesOptions<KotlinSourceSet>,
    RawConfigOptions<KotlinProjectMultiplatformExtension> {

    override val features: FeaturesExtension = objects.newInstance()

    override var target: Int = KotlinJvmOptions.DefaultJvmTarget

    override val Project.sourceSets: NamedDomainObjectContainer<KotlinSourceSet>
        get() = the<KotlinProjectMultiplatformExtension>().sourceSets

    private val common: KotlinMultiplatformCommonExtension = objects.newInstance()

    public fun Project.common(action: Action<KotlinMultiplatformCommonExtension> = Action {}) {
        project.pluginManager.apply(PluginIds.Kotlin.multiplatform)
        action.execute(common)
    }

    private val android: KotlinMultiplatformAndroidExtension = objects.newInstance()

    public fun Project.android(action: Action<KotlinMultiplatformAndroidExtension> = Action {}) {
        project.pluginManager.apply(PluginIds.Kotlin.multiplatform)
        pluginManager.apply(PluginIds.Android.library)
        action.execute(android)
        hubdleState.kotlin.multiplatform.android.isEnabled = true
    }

    private val jvm: KotlinMultiplatformJvmExtension = objects.newInstance()

    public fun Project.jvm(action: Action<KotlinMultiplatformJvmExtension> = Action {}) {
        project.pluginManager.apply(PluginIds.Kotlin.multiplatform)
        action.execute(jvm)
        hubdleState.kotlin.multiplatform.jvm.isEnabled = true
    }

    override fun Project.rawConfig(action: Action<KotlinProjectMultiplatformExtension>) {
        project.pluginManager.apply(PluginIds.Kotlin.multiplatform)
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
