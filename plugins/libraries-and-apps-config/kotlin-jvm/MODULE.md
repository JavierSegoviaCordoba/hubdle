# Module kotlin-jvm

A custom plugin for Kotlin JVM which uses `kotlin("jvm")` under the hood.

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:kotlin-jvm:$version")
}
```

### Apply

Add it to all Kotlin JVM modules in the project

```kotlin
plugins {
    `javiersc-kotlin-jvm`
}
```

### Usage

Just apply it, it has a series of default options enabled, for example, an improved project
structure
