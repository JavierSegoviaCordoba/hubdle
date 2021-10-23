@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.gradle.plugins.publish

import org.gradle.api.Project

val Project.isAndroidLibrary: Boolean
    get() = plugins.hasPlugin("com.android.library")

val Project.isGradlePlugin: Boolean
    get() = plugins.hasPlugin("org.gradle.java-gradle-plugin")

val Project.isKotlinJvm: Boolean
    get() = plugins.hasPlugin("org.jetbrains.kotlin.jvm")

val Project.isKotlinMultiplatform: Boolean
    get() = plugins.hasPlugin("org.jetbrains.kotlin.multiplatform")

val Project.isKotlinMultiplatformWithAndroid: Boolean
    get() = isAndroidLibrary && isKotlinMultiplatform

val Project.isVersionCatalog: Boolean
    get() = plugins.hasPlugin("org.gradle.version-catalog")
