# Module kotlin-config

A Gradle plugin for autoconfigure any type of project:.

- Android applications:
    - `id("com.android.application")`
- Android libraries:
    - `id("com.android.library")`
- Gradle plugins:
    - `java-gradle-plugin`
    - `kotlin-dsl`
- Java Platforms:
    - `java-platform`
- Kotlin JVM libraries:
    - `kotlin("jvm")`
    - `id("org.jetbrains.kotlin.jvm")`
- Kotlin Multiplatform libraries:
    - `kotlin("multiplatform")`
    - `id("org.jetbrains.kotlin.multiplatform")`

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:kotlin-config:$version")
}
```

### Apply

Add it to all libraries modules in the project bellow the main plugin

```kotlin
plugins {
    // ... (i.e. kotlin("jvm")
    `javiersc-kotlin-config`
}
```

### Usage

Just apply it and add the rest of plugins. It has a series of default options enabled, for example,
an improved project structure without having to add the `src` dir.

#### Android library

```kotlin
plugins {
    id("com.android.library")
    kotlin("android")
    `javiersc-kotlin-config`
}
```

#### Kotlin JVM

```kotlin
plugins {
    kotlin("jvm")
    `javiersc-kotlin-config`
}
```

#### Kotlin Multiplatform without Android

```kotlin
plugins {
    kotlin("multiplatform")
    `javiersc-kotlin-config`
}
```

#### Kotlin Multiplatform with Android

```kotlin
plugins {
    kotlin("multiplatform")
    id("com.android.library")
    `javiersc-kotlin-config`
}
```
