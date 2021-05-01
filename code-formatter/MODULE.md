# Module code-formatter

A custom plugin for code formatting which uses [ktfmt](https://github.com/facebookincubator/ktfmt) 
and [Spotless](https://github.com/diffplug/spotless) under the hood

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:code-formatter:$version")
}
```

### Apply

In the root `build.gradle.kts`

```kotlin
plugins {
    `javiersc-code-formatter`
}
```

### Usage

Completely configured, and it adds automatically the `ktfmt.xml` config used by the IDEA plugin, so
the IDEA plugin is enabled by default.
