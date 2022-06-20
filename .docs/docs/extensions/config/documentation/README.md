# Readme

Sync README with the current state of the project by running

```shell
./gradlew writeReadmeBadges
```

## Configuration

### Basic config

```kotlin
hubdle {
    config {
        documentation {
            readme {
                badges()
            }
        }
    }
}
```

### Advanced config

```kotlin
hubdle {
    config {
        documentation {
            readme {
                badges {
                    isEnabled = true
                    kotlin = true
                    mavenCentral = true
                    snapshots = true
                    build = true
                    coverage = true
                    quality = true
                    techDebt = true   
                }
            }
        }
    }
}
```
