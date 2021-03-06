buildscript {
    dependencies {
        classpath(pluginLibs.android.toolsBuild.gradle)
        classpath(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
    }
}

plugins {
    id("com.javiersc.hubdle") version "0.1.1-SNAPSHOT"
}

version = "7.3.4-beta.2"

hubdle {
    config { versioning { isEnabled = false } }

    kotlin {
        multiplatform {
            features {
                extendedStdlib(true)
                minimumTargetsPerOS()
            }

            rawConfig {
                kotlin { sourceSets.create("randomMain") }
            }

            android {
                rawConfig {
                    android {

                    }
                }
            }
            ios()
            iosArm64()
            iosX64()
            iosSimulatorArm64()
            jvm()
            js {
                browser()
                nodeJs()
            }
            linux()
            linuxX64()
            macos()
            macosArm64()
            macosX64()
            mingw()
            mingwX64()
            native()
            tvos()
            tvosArm64()
            tvosSimulatorArm64()
            tvosX64()
            wasm()
            watchos()
            watchosArm64()
            watchosX64()
            watchosSimulatorArm64()
            watchosX86()
        }
    }
}
