# Module hubdle-settings-gradle-plugin

# Hubdle settings plugin

## Usage

```kotlin
plugins {
    id("com.javiersc.hubdle.settings") version "$version"
}

hubdle {
    autoInclude {        
        excludes(":some-project")
        excludedBuilds("some-dir/some-build")
    }
}
```
