# Format

Check or apply the code format via Spotless with Ktfmt.

## Configuration

### Basic config

```kotlin
hubdle {
    kotlin {
        format()
    }
}
```

### Advanced config

```kotlin
hubdle {
    kotlin {
        format {
            isEnabled = true
            includes = mutableListOf("*/kotlin/**/*.kt", "src/*/kotlin/**/*.kt")
            excludes =
                mutableListOf(
                    "*/resources/**/*.kt",
                    "src/*/resources/**/*.kt",
                    "**/build/**",
                    "**/.gradle/**",
                )
            ktfmtVersion = "0.39"
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
