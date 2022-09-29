package com.javiersc.hubdle.extensions._internal

internal object PluginIds {

    object Analysis {
        const val detekt = "io.gitlab.arturbosch.detekt"
        const val sonarqube = "org.sonarqube"
    }

    object Android {
        const val application = "com.android.application"
        const val library = "com.android.library"
        const val kotlin = "kotlin-android"
    }

    object Documentation {
        const val changelog = "org.jetbrains.changelog"
        const val mkdocs = "ru.vyarus.mkdocs"
    }

    object Format {
        const val spotless = "com.diffplug.spotless"
    }

    object Gradle {
        const val application = "org.gradle.application"
        const val javaGradlePlugin = "java-gradle-plugin"
        const val versionCatalog = "org.gradle.version-catalog"
    }

    object JavierSC {
        const val semver = "com.javiersc.semver.gradle.plugin"
    }

    object JetBrains {
        const val intellij = "org.jetbrains.intellij"
    }

    object Kotlin {
        const val binaryCompatibilityValidator =
            "org.jetbrains.kotlinx.binary-compatibility-validator"
        const val compose = "org.jetbrains.compose"
        const val dokka = "org.jetbrains.dokka"
        const val jvm = "org.jetbrains.kotlin.jvm"
        const val kover = "org.jetbrains.kotlinx.kover"
        const val multiplatform = "org.jetbrains.kotlin.multiplatform"
        const val serialization = "org.jetbrains.kotlin.plugin.serialization"
    }

    object Publishing {
        const val gradlePluginPublish = "com.gradle.plugin-publish"
        const val mavenPublish = "org.gradle.maven-publish"
        const val nexusPublish = "io.github.gradle-nexus.publish-plugin"
        const val signing = "org.gradle.signing"
    }

    object Testing {
        const val logger = "com.adarshr.test-logger"
    }
}
