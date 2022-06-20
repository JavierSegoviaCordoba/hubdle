# Versioning

Enable semantic versioning for the current project.

More info [here](https://github.com/JavierSegoviaCordoba/semver-gradle-plugin)

## Configuration

### Basic config

```kotlin
hubdle {
    config {
        versioning()
    }
}
```

### Advanced config

```kotlin
hubdle {
    config {
        versioning {
            isEnabled = true
            tagPrefix = "v"
        }
    }
}
```
