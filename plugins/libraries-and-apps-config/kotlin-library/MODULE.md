# Module kotlin-library

A custom plugin which allow creating Android and Kotlin libraries easily.

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:kotlin-library:$version")
}
```

### Apply

Add it to all libraries modules in the project bellow the main plugin

```kotlin
plugins {
    // ... (i.e. kotlin("jvm")
    `javiersc-kotlin-library`
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
    `javiersc-kotlin-library`
}
```

#### Kotlin JVM

```kotlin
plugins {
    kotlin("jvm")
    `javiersc-kotlin-library`
}
```

#### Kotlin Multiplatform without Android

```kotlin
plugins {
    kotlin("multiplatform")
    `javiersc-kotlin-library`
}
```

#### Kotlin Multiplatform with Android

```kotlin
plugins {
    kotlin("multiplatform")
    id("com.android.library")
    `javiersc-kotlin-library`
}
```
