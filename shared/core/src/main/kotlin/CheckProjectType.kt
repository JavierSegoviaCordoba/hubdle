@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.gradle.plugins.core

import org.gradle.api.Project

val Project.isAndroidApplication: Boolean
    get() = pluginManager.hasPlugin("com.android.application")

val Project.isAndroidLibrary: Boolean
    get() = pluginManager.hasPlugin("com.android.library") && isKotlinMultiplatform.not()

val Project.isGradlePlugin: Boolean
    get() = pluginManager.hasPlugin("org.gradle.java-gradle-plugin")

val Project.isJavaPlatform: Boolean
    get() = pluginManager.hasPlugin("org.gradle.java-platform")

val Project.isKotlinJvm: Boolean
    get() = pluginManager.hasPlugin("org.jetbrains.kotlin.jvm")

val Project.isKotlinMultiplatform: Boolean
    get() = pluginManager.hasPlugin("org.jetbrains.kotlin.multiplatform")

val Project.isKotlinMultiplatformWithAndroid: Boolean
    get() = isKotlinMultiplatform && pluginManager.hasPlugin("com.android.library")

val Project.isVersionCatalog: Boolean
    get() = pluginManager.hasPlugin("org.gradle.version-catalog")
