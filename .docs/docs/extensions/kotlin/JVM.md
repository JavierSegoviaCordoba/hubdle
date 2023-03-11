# Kotlin JVM

## Overview

- jvm
    - `isEnabled: Property<Boolean>` // false
    - `enabled(value: Boolean = true)`
    - features
        - application
        - compose
        - coroutines
        - extendedStdlib
        - gradle
        - intellij
        - kotest
        - serialization
        - toolchain
    - `main(action: Action<KotlinSourceSet>)`
    - `test(action: Action<KotlinSourceSet>)`
    - `kotlin(action: Action<KotlinJvmProjectExtension>)`


## Configuration

### Basic config

```kotlin
hubdle {
    kotlin {
        jvm()
    }
}
```

### Advanced config

```kotlin
hubdle {
    kotlin {
        jvm {
            isEnabled.set(true)
            
            features {
                coroutines()
                extendedGradle()
                extendedStdlib()
                jvmToolchain {
                    javaVersion(JavaVersion.VERSION_1_8)
                }
            }
            
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
```
