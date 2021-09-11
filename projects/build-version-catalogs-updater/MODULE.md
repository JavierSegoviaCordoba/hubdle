# Module build-version-catalogs-updater

A plugin for updating Build Version Catalogs

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:build-version-catalogs-updater:$version")
}
```

### Apply

Add it to the root `build.gradle.kts`

```kotlin
plugins {
    `javiersc-build-version-catalogs-updater`
}
```

### Usage

 ```shell
 ./gradlew updateBuildVersionCatalogs

 ```
