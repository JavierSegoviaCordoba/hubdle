# Module accessors

Set of accessors for `javiersc` plugins and more

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:accessors:$version")
}
```

### Usage

It allows adding any `javiersc` plugin easily:

```kotlin
plugins {
    javiersc("id", "version")
}
```

### Samples

```kotlin
plugins {
    javiersc("docs")
}
```

```kotlin
plugins {
    javiersc("docs", "1.0.0")
}
```
