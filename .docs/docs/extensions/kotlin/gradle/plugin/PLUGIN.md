# Gradle plugin

## Configuration

### Basic config

```kotlin
hubdle {
    kotlin {
        gradle {
            plugin()
        }
    }
}
```

### Advanced config

```kotlin
hubdle {
    kotlin {
        gradle {
            plugin { 
                isEnabled.set(true)
                
                features {
                    coroutines()                    
                    extendedGradle()
                    extendedStdlib()
                    jvmToolchain {
                        javaVersion(JavaVersion.VERSION_1_8)
                    }
                }
                
                tags("hubdle", "...")
                
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
