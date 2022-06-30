# Kotlin JVM

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
            isEnabled = true
            
            features {
                coroutines(false)
                extendedGradle(false)
                extendedStdlib(true)
            }

            jvmVersion = 8

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

            rawConfig {
                kotlin {
                    // KotlinJvmProjectExtension
                }
            }
        }
    }
}
```
