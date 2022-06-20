# Site

Build a site easily by running

```shell
./gradlew buildSite
```

## Configuration

### Basic config

```kotlin
hubdle {
    config {
        documentation {
            site()
        }
    }
}
```

### Advanced config

```kotlin
hubdle {
    config {
        documentation {
            site {
                isEnabled = true
                reports {
                    allTests = true
                    codeAnalysis = true
                    codeCoverage = true
                    codeQuality = true
                }
            }
        }
    }
}
```
