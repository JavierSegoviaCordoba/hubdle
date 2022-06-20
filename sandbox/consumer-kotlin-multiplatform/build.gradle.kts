import com.android.build.api.dsl.LibraryExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

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
        multiplatform {
            features {
                javierScStdlib(false)
            }

            rawConfig {
                android {
                    compileSdk = 29
                }

                kotlin {
                    sourceSets.create("randomMain")
                }
            }

            android()
            ios()
            iosArm32()
            iosArm64()
            iosX64()
            iosSimulatorArm64()
            jvm()
            js {
                browser()
                nodeJs()
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
