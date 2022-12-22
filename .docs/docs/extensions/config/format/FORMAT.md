# Format

Check or apply the code format via Spotless with Ktfmt.

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
