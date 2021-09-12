# Module kotlin-multiplatform-no-android

A custom plugin for Kotlin Multiplatform which uses `kotlin("multitiplatform")` under the hood.

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:kotlin-multiplatform-no-android:$version")
}
```

### Apply

Add it to all Kotlin Multiplatform modules in the project

```kotlin
plugins {
    `javiersc-kotlin-multiplatform-no-android`
}
```

### Usage

Just apply it, it has a series of default options enabled, for example, remove warning for some
experimental features.
