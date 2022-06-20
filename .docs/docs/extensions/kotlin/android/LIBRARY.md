# Android library

## Configuration

### Basic config

```kotlin
hubdle {
    kotlin {
        android {
            library()
        }
    }
}
```

### Advanced config

```kotlin
hubdle {
    kotlin {
        android {
            library { 
                isEnabled = true
                
                features {
                    coroutines(false)
                    javierScStdlib(true)
                }
                
                compileSdk = 31
                minSdk = 21
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
                    android { 
                       // LibraryExtension
                    }
                }
            }
        }
    }
}
```
