package com.javiersc.hubdle.extensions._internal

internal object PluginIds {

    object Analysis {
        const val detekt = "io.gitlab.arturbosch.detekt"
        const val sonarqube = "org.sonarqube"
    }

    object Android {
        const val library = "com.android.library"
    }

    object Documentation {
        const val changelog = "org.jetbrains.changelog"
    }

    object Format {
        const val spotless = "com.diffplug.spotless"
    }

    object Gradle {
        const val javaGradlePlugin = "java-gradle-plugin"
        const val versionCatalog = "org.gradle.version-catalog"
    }

    object JavierSC {
        const val semver = "com.javiersc.semver.gradle.plugin"
    }

    object Kotlin {
        const val jvm = "org.jetbrains.kotlin.jvm"
        const val multiplatform = "org.jetbrains.kotlin.multiplatform"
    }

    object Publishing {
        const val gradlePluginPublish = "com.gradle.plugin-publish"
        const val mavenPublish = "org.gradle.maven-publish"
        const val nexusPublish = "io.github.gradle-nexus.publish-plugin"
        const val signing = "org.gradle.signing"
    }
}
