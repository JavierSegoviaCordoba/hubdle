# Gradle Version Catalog

## Configuration

### Basic config

```kotlin
hubdle {
    kotlin {
        gradle {
            versionCatalog {
                catalogs(file("libs.versions.toml"), file("another.toml"))
            }
        }
    }
}
```
