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
