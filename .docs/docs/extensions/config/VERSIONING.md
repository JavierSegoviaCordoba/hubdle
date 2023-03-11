# Versioning

Enable semantic versioning for the current project.

More info [here](https://github.com/JavierSegoviaCordoba/semver-gradle-plugin)

## Overview

- versioning
    - `isEnabled: Property<Boolean>` // true
    - `enabled(value: Boolean = true)`
    - `tagPrefix: Property<String>` // `""` (empty string)
    - `semver(action: Action<SemverExtension>)`


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
            isEnabled.set(true)
            tagPrefix.set("v")
        }
    }
}
```
