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
            isEnabled.set(false)
        }
    }

    kotlin {
        multiplatform {
            features {
                extendedStdlib.isEnabled.set(false)
                kotest.isEnabled.set(false)
                minimumTargetPerOs()
            }

            kotlin {
                sourceSets.create("randomMain")
            }
            android {
                android {
                    compileSdk = 29
                }
            }
            androidNative {
                androidNativeArm32()
                androidNativeArm64()
                androidNativeX64()
                androidNativeX86()
            }
            apple {
                ios {
                    iosArm64()
                    iosX64()
                    iosSimulatorArm64()
                }
                macos {
                    macosArm64()
                    macosX64()
                }
                tvos {
                    tvosArm64()
                    tvosSimulatorArm64()
                    tvosX64()
                }
                watchos {
                    watchosArm32()
                    watchosArm64()
                    watchosX64()
                    watchosSimulatorArm64()
                }
            }
            jvm()
            js {
                browser()
                nodejs()
            }
            linux {
                linuxArm64()
                linuxX64()
            }
            mingw {
                mingwX64()
            }
            native()
            wasm()
        }
    }
}

println("RawConfig compileSdk: ${the<LibraryExtension>().compileSdk}")

val kmpSourceSets = the<KotlinMultiplatformExtension>().sourceSets

println("RawConfig sourceSets: \n${kmpSourceSets.joinToString("\n- "){it.name} }")
