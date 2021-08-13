# Module build-version-catalog

Build Version Catalogs from Gradle files which allow dependabot compatibility

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies { 
    implementation("com.javiersc.gradle-plugins:build-version-catalogs:$version") 
}
```

### Apply

In the `settings.gradle.kts`

```kotlin
plugins {
    id("com.javiersc.gradle.plugins.build.version.catalogs")
}
```

### Usage

Create a project which includes in the `build.gradle.kts`:

```kotlin
@file:Suppress("PropertyName")

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

This will auto-generate and auto-add a Version Catalog called `libs`
