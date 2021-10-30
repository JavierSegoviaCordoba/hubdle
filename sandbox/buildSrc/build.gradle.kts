plugins {
    `kotlin-dsl`
}
repositories {
    mavenLocal {
        content { includeGroup("com.javiersc.gradle-plugins") }
    }
    gradlePluginPortal()
    mavenCentral()
    google()
    maven("https://oss.sonatype.org/content/repositories/snapshots/") {
        content { includeGroup("com.javiersc.gradle-plugins") }
    }
}

dependencies {
    implementation(pluginLibs.javiersc.gradlePlugins.allPlugins)
    implementation(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
}
