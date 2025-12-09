package com.javiersc.hubdle.project.extensions._internal

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare
import com.javiersc.hubdle.project.extensions.shared.PluginId
import com.javiersc.semver.project.gradle.plugin.SemverExtension
import dev.detekt.gradle.extensions.DetektExtension
import kotlinx.validation.ApiValidationExtension
import org.gradle.api.Project
import org.gradle.api.plugins.JavaApplication
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension as GradlePublishingExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension
import org.gradle.plugins.signing.SigningExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal inline fun Project.withAndroidApplication(
    crossinline block: ApplicationExtension.() -> Unit
) = pluginManager.withPlugin(PluginId.AndroidApplication.id) { block(extensions.getByType()) }

internal inline fun Project.withAndroidLibrary(crossinline block: LibraryExtension.() -> Unit) =
    pluginManager.withPlugin(PluginId.AndroidLibrary.id) { block(extensions.getByType()) }

internal inline fun Project.withCompose(crossinline block: Unit.() -> Unit) =
    pluginManager.withPlugin(PluginId.JetbrainsCompose.id) { block(extensions.getByType()) }

internal inline fun Project.withDetekt(crossinline block: DetektExtension.() -> Unit) =
    pluginManager.withPlugin(PluginId.Detekt.id) { block(extensions.getByType()) }

internal inline fun Project.withGradleApplication(crossinline block: JavaApplication.() -> Unit) =
    pluginManager.withPlugin(PluginId.GradleApplication.id) { block(extensions.getByType()) }

internal inline fun Project.withGradlePlugin(
    crossinline block: GradlePluginDevelopmentExtension.() -> Unit
) = pluginManager.withPlugin(PluginId.JavaGradlePlugin.id) { block(extensions.getByType()) }

internal inline fun Project.withJava(crossinline block: JavaPluginExtension.() -> Unit) =
    pluginManager.withPlugin(PluginId.Java.id) { block(extensions.getByType()) }

internal inline fun Project.withKotlin(crossinline block: KotlinProjectExtension.() -> Unit) {
    withKotlinAndroid(block)
    withKotlinJvm(block)
    withKotlinMultiplatform(block)
}

internal inline fun Project.withKotlinAndroid(
    crossinline block: KotlinAndroidProjectExtension.() -> Unit
) = pluginManager.withPlugin(PluginId.JetbrainsKotlinAndroid.id) { block(extensions.getByType()) }

internal inline fun Project.withKotlinJvm(crossinline block: KotlinJvmProjectExtension.() -> Unit) =
    pluginManager.withPlugin(PluginId.JetbrainsKotlinJvm.id) { block(extensions.getByType()) }

internal inline fun Project.withKotlinMultiplatform(
    crossinline block: KotlinMultiplatformExtension.() -> Unit
) =
    pluginManager.withPlugin(PluginId.JetbrainsKotlinMultiplatform.id) {
        block(extensions.getByType())
    }

internal inline fun Project.withKotlinBinaryCompatibilityValidator(
    crossinline block: ApiValidationExtension.() -> Unit
) =
    pluginManager.withPlugin(PluginId.JetbrainsKotlinxBinaryCompatibilityValidator.id) {
        block(extensions.getByType())
    }

internal inline fun Project.withPublishing(
    crossinline block: GradlePublishingExtension.() -> Unit
) = pluginManager.withPlugin(PluginId.MavenPublish.id) { block(extensions.getByType()) }

internal inline fun Project.withSemver(crossinline block: SemverExtension.() -> Unit) =
    pluginManager.withPlugin(PluginId.JavierscSemverGradlePlugin.id) {
        block(extensions.getByType())
    }

internal inline fun Project.withSigning(crossinline block: SigningExtension.() -> Unit) =
    pluginManager.withPlugin(PluginId.GradleSigning.id) { block(extensions.getByType()) }

internal inline fun Project.withSpotless(crossinline block: SpotlessExtension.() -> Unit) =
    pluginManager.withPlugin(PluginId.DiffplugSpotless.id) { block(extensions.getByType()) }

internal inline fun Project.withSpotlessPredeclare(
    crossinline block: SpotlessExtensionPredeclare.() -> Unit
) = pluginManager.withPlugin(PluginId.DiffplugSpotless.id) { block(extensions.getByType()) }
