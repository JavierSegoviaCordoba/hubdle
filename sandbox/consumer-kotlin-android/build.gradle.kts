buildscript {
    dependencies {
        classpath(pluginLibs.android.toolsBuild.gradle)
        classpath(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
    }
}

plugins {
    id("com.javiersc.hubdle") version "0.2.1-SNAPSHOT"
}

hubdle {
    config {
        versioning {
            isEnabled = false
        }
    }

    kotlin {
        android {
            library {
                main {
                    dependencies {
                        implementation(pluginLibs.javiersc.semver.semverCore)
                    }
                }
            }
        }
    }
}
