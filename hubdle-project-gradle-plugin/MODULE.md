# Module hubdle-gradle-plugin

# Hubdle

## Usage

```kotlin
plugins {
    id("com.javiersc.hubdle") version "$version"
}

// basic config to get a Kotlin jvm project with coroutines and a custom dependency
hubdle {
    kotlin {
        jvm {
            features {
                coroutines()
            }
            
            main {
                dependencies {
                    implementation("org.example-group:example:1.0.0")
                }
            }
        }
    }
}
```

## Configurations

Visit the [website](https://hubdle.javiersc.com/extensions/)
