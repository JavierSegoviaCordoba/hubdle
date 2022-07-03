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
                isEnabled = true
                
                features {
                    coroutines(false)                    
                    extendedGradle(true)
                    extendedStdlib(true)
                }

                jvmVersion = 8
                
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
                
                rawConfig {
                    kotlin { 
                        // KotlinJvmProjectExtension
                    }
                    
                    gradlePlugin {
                        // GradlePluginDevelopmentExtension    
                    }
                }
            }
        }
    }
}
```
