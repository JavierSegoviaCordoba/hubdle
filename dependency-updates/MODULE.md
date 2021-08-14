# Module dependency-updates

A custom plugin for to find the latest versions for all dependencies which uses
[ben-manes](https://github.com/ben-manes/gradle-versions-plugin) under the hood.

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:dependency-updates:$version")
}
```

### Apply

In the root `build.gradle.kts`

```kotlin
plugins {
    `javiersc-dependency-updates`
}
```

### Usage

Just run the task

```shell
./gradlew dependencyUpdates
```

Add to `gradle.properties` the property `dependencyDiscoveryLimit` to define the limit for the
dependencies.

Options:

- `SNAPSHOT`
- `dev`
- `eap`
- `alpha`
- `beta`
- `M` (or `milestone`)
- `rc`
- `stable`

### Samples

```properties
dependencyDiscoveryLimit=stable
```
