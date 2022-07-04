buildscript {
    dependencies {
        classpath(pluginLibs.android.toolsBuild.gradle)
        classpath(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
    }
}

plugins {
    id("com.javiersc.hubdle") version "0.1.1-SNAPSHOT"
}

hubdle {
    config {
        versioning {
            isEnabled = false
        }
    }

    kotlin {
        jvm()
    }
}
