# Format

Check or apply the code format via Spotless with Ktfmt.

## Overview

- format
    - `isEnabled: Property<Boolean>` // true
    - `enabled(value: Boolean = true)`
    - `includes: SetProperty<String>`
    - `includes(vararg values: String)`
    - `excludes: SetProperty<String>`
    - `excludes(vararg values: String)`
    - `ktfmtVersion: Property<String>`
    - `spotless(action: Action<SpotlessExtension>)`
    - `spotlessPredeclare(action: Action<SpotlessPredeclare>)`

## Configuration

### Basic config

```kotlin
hubdle {
    config {
        format()
    }
}
```

### Advanced config

```kotlin
hubdle {
    config {
        format {
            isEnabled.set(true)
            includes.set(setOf("*/kotlin/**/*.kt", "src/*/kotlin/**/*.kt"))
            excludes.set(
                setOf(
                    "*/resources/**/*.kt",
                    "src/*/resources/**/*.kt",
                    "**/build/**",
                    "**/.gradle/**",
                )
            )
            ktfmtVersion.set("0.39")
        }
    }
}

```

### Tasks

```shell
./gradlew checkFormat
```

```shell
./gradlew applyFormat
```
