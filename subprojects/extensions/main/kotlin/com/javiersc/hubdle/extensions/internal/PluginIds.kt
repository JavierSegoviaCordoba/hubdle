package com.javiersc.hubdle.extensions.internal

internal object PluginIds {

    object Android {
        const val library = "com.android.library"
    }

    object Format {
        const val spotless = "com.diffplug.spotless"
    }

    object JavierSC {
        const val semver = "com.javiersc.semver.gradle.plugin"
    }

    object Kotlin {
        const val dsl = "org.gradle.kotlin.kotlin-dsl"
        const val jvm = "org.jetbrains.kotlin.jvm"
        const val multiplatform = "org.jetbrains.kotlin.multiplatform"
    }

    object Publishing {
        const val gradlePluginPublish = "com.gradle.plugin-publish"
        const val mavenPublish = "org.gradle.maven-publish"
        const val signing = "org.gradle.signing"
    }
}
