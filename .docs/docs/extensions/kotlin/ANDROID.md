# Android

## Overview

- android
    - `isEnabled: Property<Boolean>` // false
    - `enabled(value: Boolean = true)`
    - `namespace: Property<String?>` // null
    - `minSdk: Property<Int>` // 23
    - `compileSdk: Property<Int>` // 33
    - `targetSdk: Property<Int>` // 33
    - application
        - `isEnabled: Property<Boolean>` // false
        - `enabled(value: Boolean = true)`
        - features
            - compose
            - coroutines
            - extendedStdlib
            - kotest
            - serialization
            - toolchain
        - `applicationId: Property<String?>` // `namespace`
        - `versionCode: Property<Int>` // 1
        - `versionName: Property<String>` // 0.1.0
        - `configuration(name: String, action: Action<Configuration>)`
        - `sourceSet(name: String, action: Action<KotlinSourceSet>)`
        - `main(action: Action<KotlinSourceSet>)`
        - `test(action: Action<KotlinSourceSet>)`
        - `android(action: Action<ApplicationExtension>)`
    - library
        - `isEnabled: Property<Boolean>` // false
        - `enabled(value: Boolean = true)`
        - features
            - compose
            - coroutines
            - extendedStdlib
            - kotest
            - serialization
            - toolchain
        - `configuration(name: String, action: Action<Configuration>)`
        - `sourceSet(name: String, action: Action<KotlinSourceSet>)`
        - `main(action: Action<KotlinSourceSet>)`
        - `test(action: Action<KotlinSourceSet>)`
        - `android(action: Action<LibraryExtension>)`

## library

### Configuration

#### Basic config

```kotlin
hubdle {
    kotlin {
        android {
            library()
        }
    }
}
```

#### Advanced config

```kotlin
hubdle {
    kotlin {
        android {
            library {
                isEnabled.set(true)

                features {
                    coroutines()
                    extendedStdlib()
                }

                compileSdk.set(31)
                minSdk.set(21)

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
        }
    }
}
```
