# Module code-analysis

A custom plugin for code analysis which uses [Detekt](https://github.com/detekt/detekt) under the 
hood.

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies { 
    implementation("com.javiersc.gradle-plugins:code-analysis:$version") 
}
```

### Apply

In the root `build.gradle.kts`

```kotlin
plugins {
    `javiersc-code-analysis`
}
```

### Usage

Completely configured, and it adds automatically the `detekt.xml` config used by the IDEA plugin, so
the IDEA plugin is enabled by default.
