@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.gradle.plugins.core

import org.gradle.api.Project
import org.gradle.api.plugins.AppliedPlugin

val Project.isAndroidLibrary: Boolean
    get() = pluginManager.hasPlugin("com.android.library") && isKotlinMultiplatform.not()

fun Project.withAndroidLibrary(appliedPlugin: AppliedPlugin.() -> Unit) =
    pluginManager.withPlugin("com.android.library", appliedPlugin)

val Project.isGradlePlugin: Boolean
    get() = pluginManager.hasPlugin("org.gradle.java-gradle-plugin")

val Project.isKotlinJvm: Boolean
    get() = pluginManager.hasPlugin("org.jetbrains.kotlin.jvm")

fun Project.withKotlinJvm(appliedPlugin: AppliedPlugin.() -> Unit) =
    pluginManager.withPlugin("org.jetbrains.kotlin.jvm", appliedPlugin)

val Project.isKotlinMultiplatform: Boolean
    get() = pluginManager.hasPlugin("org.jetbrains.kotlin.multiplatform")

fun Project.withKotlinMultiplatform(appliedPlugin: AppliedPlugin.() -> Unit) =
    pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform", appliedPlugin)

val Project.isKotlinMultiplatformWithAndroid: Boolean
    get() = isKotlinMultiplatform && pluginManager.hasPlugin("com.android.library")

fun Project.withKotlinMultiplatformWithAndroid(
    appliedKotlinMultiplatform: AppliedPlugin.() -> Unit,
    appliedAndroidLibrary: AppliedPlugin.() -> Unit,
) {
    pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform", appliedKotlinMultiplatform)
    pluginManager.withPlugin("com.android.library", appliedAndroidLibrary)
}

val Project.isVersionCatalog: Boolean
    get() = pluginManager.hasPlugin("org.gradle.version-catalog")
