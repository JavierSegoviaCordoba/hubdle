# Module versioning

A custom plugin for git tags versioning which used
[reckon plugin](https://github.com/ajoberstar/reckon) under the hood.

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:versioning:$version")
}
```

### Apply

In the root `build.gradle.kts`

```kotlin
plugins {
    `javiersc-versioning`
}
```

### Usage

Check Reckon [readme](https://github.com/ajoberstar/reckon) or
[samples](https://github.com/ajoberstar/reckon/blob/main/docs/index.md) to see how it works

Check samples about how to manage the project version with GitHub Actions in 
[.github/workflows](../.github/workflows)
