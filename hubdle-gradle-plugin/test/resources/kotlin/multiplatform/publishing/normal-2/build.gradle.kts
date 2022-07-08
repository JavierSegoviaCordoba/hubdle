import com.android.build.api.dsl.LibraryExtension
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    id("com.javiersc.hubdle")
}

version = "7.3.4-beta.2"

hubdle {
    config {
        publishing()

        versioning {
            isEnabled = false
        }
    }

    kotlin {
        multiplatform {
            features {
                extendedStdlib(false)
                minimumTargetsPerOS()
            }

            rawConfig {
                kotlin { sourceSets.create("randomMain") }
            }

            android {
                rawConfig {
                    android { compileSdk = 29 }
                }
            }
            ios()
            iosArm32()
            iosArm64()
            iosX64()
            iosSimulatorArm64()
            jvm()
            js {
                browser()
                nodejs()
            }
            linux()
            linuxArm32Hfp()
            linuxArm64()
            linuxMips32()
            linuxMipsel32()
            linuxX64()
            macos()
            macosArm64()
            macosX64()
            mingw()
            mingwX64()
            mingwX86()
            native()
            tvos()
            tvosArm64()
            tvosSimulatorArm64()
            tvosX64()
            wasm()
            wasm32()
            watchos()
            watchosArm32()
            watchosArm64()
            watchosX64()
            watchosSimulatorArm64()
            watchosX86()
        }
    }
}

println("RawConfig compileSdk: ${the<LibraryExtension>().compileSdk}")

val kmpSourceSets = the<KotlinMultiplatformExtension>().sourceSets

println("RawConfig sourceSets: \n${kmpSourceSets.joinToString("\n- "){it.name} }")
