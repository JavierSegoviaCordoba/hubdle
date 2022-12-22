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
