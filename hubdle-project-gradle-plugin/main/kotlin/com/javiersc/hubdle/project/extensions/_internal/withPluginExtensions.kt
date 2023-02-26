package com.javiersc.hubdle.project.extensions._internal

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare
import com.javiersc.semver.gradle.plugin.SemverExtension
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import kotlinx.kover.api.KoverProjectConfig
import kotlinx.validation.ApiValidationExtension
import org.gradle.api.Project
import org.gradle.api.plugins.JavaApplication
import org.gradle.api.publish.PublishingExtension as GradlePublishingExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension
import org.gradle.plugins.signing.SigningExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal fun Project.withId(id: PluginId, block: () -> Unit) {
    pluginManager.withPlugin("$id") { block() }
}

internal inline fun <reified T : Any> Project.withId(
    id: PluginId,
    crossinline block: T.() -> Unit
) {
    pluginManager.withPlugin("$id") { block(extensions.getByType()) }
}

internal fun Project.withAndroidApplication(block: ApplicationExtension.() -> Unit) =
    withId(id = PluginId.AndroidApplication, block = block)

internal fun Project.withAndroidLibrary(block: LibraryExtension.() -> Unit) =
    withId(id = PluginId.AndroidLibrary, block = block)

internal fun Project.withCompose(block: () -> Unit) =
    withId(id = PluginId.JetbrainsCompose, block = block)

internal fun Project.withDetekt(block: DetektExtension.() -> Unit) =
    withId(id = PluginId.Detekt, block = block)

internal fun Project.withGradleApplication(block: JavaApplication.() -> Unit) =
    withId(id = PluginId.GradleApplication, block = block)

internal fun Project.withGradlePlugin(block: GradlePluginDevelopmentExtension.() -> Unit) =
    withId(id = PluginId.JavaGradlePlugin, block = block)

internal fun Project.withKotlin(block: KotlinProjectExtension.() -> Unit) {
    withKotlinAndroid(block)
    withKotlinJvm(block)
    withKotlinMultiplatform(block)
}

internal fun Project.withKotlinAndroid(block: KotlinAndroidProjectExtension.() -> Unit) =
    withId(id = PluginId.JetbrainsKotlinAndroid, block = block)

internal fun Project.withKotlinJvm(block: KotlinJvmProjectExtension.() -> Unit) =
    withId(id = PluginId.JetbrainsKotlinJvm, block = block)

internal fun Project.withKotlinMultiplatform(block: KotlinMultiplatformExtension.() -> Unit) =
    withId(id = PluginId.JetbrainsKotlinMultiplatform, block = block)

internal fun Project.withKotlinBinaryCompatibilityValidator(
    block: ApiValidationExtension.() -> Unit
) = withId(id = PluginId.JetbrainsKotlinxBinaryCompatibilityValidator, block = block)

internal fun Project.withKover(block: KoverProjectConfig.() -> Unit) =
    withId(id = PluginId.JetbrainsKotlinxKover, block = block)

internal fun Project.withPublishing(block: GradlePublishingExtension.() -> Unit) =
    withId(id = PluginId.MavenPublish, block = block)

internal fun Project.withSemver(block: SemverExtension.() -> Unit) =
    withId(id = PluginId.JavierscSemverGradlePlugin, block = block)

internal fun Project.withSigning(block: SigningExtension.() -> Unit) =
    withId(id = PluginId.GradleSigning, block = block)

internal fun Project.withSpotless(block: SpotlessExtension.() -> Unit) =
    withId(id = PluginId.DiffplugSpotless, block = block)

internal fun Project.withSpotlessPredeclare(block: SpotlessExtensionPredeclare.() -> Unit) =
    withId(id = PluginId.DiffplugSpotless, block = block)
