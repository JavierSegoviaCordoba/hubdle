# Module gradle-wrapper-updater

A plugin for updating the Gradle Wrapper

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:gradle-wrapper-updater:$version")
}
```

### Apply

Add it to the root `build.gradle.kts`

```kotlin
plugins {
    `javiersc-gradle-wrapper-updater`
}
```

### Usage

 ```shell
 ./gradlew updateGradlew

 ```

#### Set the stage to get the latest Release Candidate or Nightly

 ```shell
./gradlew updateGradlew -P"gradlew.stage"="rc"
 ```

### Notes

> - If the input is not set, or it is `current`, the last stable version will be used.
> - If the input is `rc`, the last release candidate version will be used, can be possible there is no release candidate version.
> - If the input is `nightly`, the last nightly version will be used.
