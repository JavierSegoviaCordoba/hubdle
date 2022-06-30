# Module hubdle

# Hubdle

## Usage

```kotlin
plugins {
    id("com.javiersc.hubdle") version "$version"
}

// basic config to get a Kotlin jvm project with coroutines
hubdle {
    kotlin {
        jvm {
            features {
                coroutines()                
            }
        }
    }
}
```

## Configurations

Check [extensions](https://hubdle.javiersc.com/)
