# Module readme-badges

Automatically add badges to the root README file.

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:readme-badges:$version")
}
```

### Apply

In the root `build.gradle.kts`

```kotlin
plugins {
    `javiersc-readme-badges-generator`
}
```

### Usage

Run the next task so all badges are generated and/or the Kotlin version is updated to the latest
version and reflected in its respective badge.

```shell
./gradlew buildReadmeBadges
```

Add to `gradle.properties`

```properties
allProjects.group=com.javiersc
allProjects.name=gradle-plugins
readmeBadges.mainProject=versioning
readmeBadges.allProjects=false # false by default, true generates badges for all projects
```
