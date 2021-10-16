# Module massive-catalogs-updater

A plugin for updating Massive Catalogs

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:massive-catalogs-updater:$version")
}
```

### Apply

Add it to the root `build.gradle.kts`

```kotlin
plugins {
    `javiersc-massive-catalogs-updater`
}
```

### Usage

 ```shell
 ./gradlew updateMassiveCatalogs

 ```
