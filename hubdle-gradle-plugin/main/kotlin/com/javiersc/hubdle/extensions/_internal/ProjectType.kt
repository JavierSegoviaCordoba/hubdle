package com.javiersc.hubdle.extensions._internal

import org.gradle.api.Project

internal val Project.isAndroidApplication: Boolean
    get() = pluginManager.hasPlugin("com.android.application")

internal val Project.isAndroidLibrary: Boolean
    get() = pluginManager.hasPlugin("com.android.library") && isKotlinMultiplatform.not()

internal val Project.isGradlePlugin: Boolean
    get() = pluginManager.hasPlugin("org.gradle.java-gradle-plugin")

internal val Project.isJavaPlatform: Boolean
    get() = pluginManager.hasPlugin("org.gradle.java-platform")

internal val Project.isKotlinJvm: Boolean
    get() = pluginManager.hasPlugin("org.jetbrains.kotlin.jvm")

internal val Project.isKotlinMultiplatform: Boolean
    get() = pluginManager.hasPlugin("org.jetbrains.kotlin.multiplatform")

internal val Project.isKotlinMultiplatformWithAndroid: Boolean
    get() = isKotlinMultiplatform && pluginManager.hasPlugin("com.android.library")

internal val Project.isVersionCatalog: Boolean
    get() = pluginManager.hasPlugin("org.gradle.version-catalog")
