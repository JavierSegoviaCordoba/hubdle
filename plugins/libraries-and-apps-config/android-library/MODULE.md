# Module kotlin-multiplatform

A custom plugin for Android libraries which uses `com.android.library` under the hood.

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:android-library:$version")
}
```

### Apply

Add it to all Android libraries modules in the project

```kotlin
plugins {
    `javiersc-android-library`
}
```

### Usage

Just apply it, it has a series of default options enabled
