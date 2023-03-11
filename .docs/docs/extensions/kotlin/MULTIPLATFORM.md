# Kotlin Multiplatform

## Overview

- multiplatform
    - `isEnabled: Property<Boolean>` // false
    - `enabled(value: Boolean = true)`
    - features
        - multiplatformMinimumTargetPerOs
        - compose
        - coroutines
        - extendedStdlib
        - kotest
        - serialization
        - toolchain
    - common
    - android
    - apple
        - ios
            - iosArm32
            - iosArm64
            - iosSimulatorArm64
            - iosX64
        - macos
            - macosArm64
            - macosX64
        - tvos
            - tvosArm64
            - tvosSimulatorArm64
            - tvosX64
        - watchos
            - watchosArm32
            - watchosArm64
            - watchosSimulatorArm64
            - watchosX64
            - watchosX86
    - jvm
    - jvmAndAndroid
    - js
        - browser
        - nodejs
    - linux
    - mingw
    - native
    - wasm
    - `kotlin(action: Action<KotlinMultiplatformExtension>)`

## Configuration

### Basic config

```kotlin
hubdle {
    kotlin {
        multiplatform {
            jvm()
        }
    }
}
```

### Advanced config

```kotlin
hubdle {
    kotlin {
        multiplatform {
            isEnabled.set(true)

            features {
                coroutines()
                extendedStdlib()
                jvmToolChain {
                    javaVersion(JavaVersion.VERSION_1_8)
                }
            }

            android {
                main {
                    dependencies {
                        implementation(libs.some.android.lib)
                    }
                }

                test {
                    dependencies {
                        implementation(libs.some.jvm.android.lib)
                    }
                }
            }

            common {
                main {
                    dependencies {
                        implementation(libs.some.lib)
                    }
                }

                test {
                    dependencies {
                        implementation(libs.some.test.lib)
                    }
                }
            }

            jvm {
                main {
                    dependencies {
                        implementation(libs.some.jvm.lib)
                    }
                }

                test {
                    dependencies {
                        implementation(libs.some.jvm.test.lib)
                    }
                }
            }
        }
    }
}
```
