# Module build-version-catalog

Build Version Catalogs from Gradle files which allow dependabot compatibility

### Apply

- _gradle.properties_

```properties
buildVersionCatalogs=1.0.0
```

- _settings.gradle.kts_

```kotlin
pluginManagement {
    repositories {
        mavenCentral()
    }
    plugins {
        val buildVersionCatalogs: String by settings

        id("com.javiersc.gradle.plugins.build.version.catalogs") version buildVersionCatalogs
    }
}

plugins {
    id("com.javiersc.gradle.plugins.build.version.catalogs")
}
```

### Usage

Create a project which includes in the `build.gradle.kts`:

```kotlin
@file:Suppress("PropertyName", "VariableNaming")

// catalog start - libs

// [versions]
val coroutines = "1.5.0"

// [libraries]
val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines"
val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines"

// [bundles]
val allCoroutines = coroutines_core + coroutines_android

// catalog end
```

This will auto-generate and auto-add a Version Catalog called `libs` in `build/catalogs/libs.toml`

> - A version inside any library can be omitted, setting only the module part
> - `bundles` can be omitted

### Add manually the catalogs to another project (i.e. `buildSrc`)

- _settings.gradle.kts_

```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("libs") { from(files("../build/catalogs/libs.toml")) }
    }
}
```
