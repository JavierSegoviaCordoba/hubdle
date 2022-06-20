package com.javiersc.hubdle.extensions.kotlin.multiplatform

import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.extensions.OriginalConfigOptions
import com.javiersc.hubdle.extensions.PublishingOptions
import com.javiersc.hubdle.extensions.SourceDirectoriesOptions
import com.javiersc.hubdle.extensions.configurePublishingExtension
import com.javiersc.hubdle.extensions.configureSigningForPublishing
import com.javiersc.hubdle.extensions.internal.PluginIds
import com.javiersc.hubdle.extensions.kotlin.jvm.KotlinJvmOptions
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformAndroidExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformCommonExtension
import com.javiersc.hubdle.extensions.kotlin.multiplatform.targets.KotlinMultiplatformJvmExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.newInstance
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension as KotlinProjectMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

public open class KotlinMultiplatformExtension
@Inject
constructor(
    objects: ObjectFactory,
) :
    KotlinJvmOptions,
    SourceDirectoriesOptions<KotlinSourceSet>,
    OriginalConfigOptions<KotlinProjectMultiplatformExtension>,
    PublishingOptions {

    override fun Project.publishing() {
        pluginManager.apply(PluginIds.Publishing.mavenPublish)
        pluginManager.apply(PluginIds.Publishing.signing)
        configurePublishingExtension()
        configureSigningForPublishing()
    }

    override var target: Int = KotlinJvmOptions.DefaultJvmTarget

    override val Project.sourceSets: NamedDomainObjectContainer<KotlinSourceSet>
        get() = kotlinMultiplatformExtension.sourceSets

    private val common: KotlinMultiplatformCommonExtension = objects.newInstance()

    public fun common(
        action: Action<KotlinMultiplatformCommonExtension> = Action {}
    ): KotlinMultiplatformCommonExtension {
        action.execute(common)

        return common
    }

    private val android: KotlinMultiplatformAndroidExtension = objects.newInstance()

    public fun Project.android(
        action: Action<KotlinMultiplatformAndroidExtension> = Action {}
    ): KotlinMultiplatformAndroidExtension {
        pluginManager.apply(PluginIds.Android.library)
        action.execute(android)

        configure<LibraryExtension> {
            sourceSets.all {
                it.manifest.srcFile("android${it.name.capitalized()}/AndroidManifest.xml")
            }
        }

        return android
    }

    private val jvm: KotlinMultiplatformJvmExtension = objects.newInstance()

    public fun Project.jvm(
        action: Action<KotlinMultiplatformJvmExtension> = Action {}
    ): KotlinMultiplatformJvmExtension {
        action.execute(jvm)
        kotlinMultiplatformExtension.jvm(jvm.name)

        return jvm
    }

    override fun Project.originalConfig(action: Action<KotlinProjectMultiplatformExtension>) {
        action.execute(kotlinMultiplatformExtension)
    }
}

internal val Project.kotlinMultiplatformExtension: KotlinProjectMultiplatformExtension
    get() = project.extensions.getByType()
