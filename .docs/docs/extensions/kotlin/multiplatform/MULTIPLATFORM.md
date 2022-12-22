# Kotlin Multiplatform

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
