# Module changelog

A custom plugin for changelog plugin which uses 
[JetBrains Changelog plugin](https://github.com/JetBrains/gradle-changelog-plugin) under the hood.

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies { 
    implementation("com.javiersc.gradle-plugins:changelog:$version") 
}
```

### Apply

In the root `build.gradle.kts`

```kotlin
plugins {
    `javiersc-changelog`
}
```

### Usage

Completely configured, check the original plugin for more info.

```shell
./gradlew mergeChangelog
```

This task has only effect when the project version is final, and it will merge the previous 
beta/alpha/rc versions into one when it is invoked. The date of the merged version will be the date 
when the task is executed.

The usual workflow when it is desired to merge all non-final versions into one when the final 
version is being released is to patch the changelog and then merge it:

```shell
./gradlew patchChangelog mergeChangelog
```

### Samples

Supposing this changelog and running the task

```markdown
# Changelog

## [Unreleased]

### Added

### Changed

### Deprecated

### Removed

### Fixed

### Updated


## [0.1.0-alpha.2] - 2021-04-27

### Added
- `changelog` can merge non-final versions into one final version
- `readme-badges-generator` prints Kotlin version

## [0.1.0-alpha.1] - 2021-04-26

### Fixed
- Kotlin version is found by checking all the projects instead of from Version Catalog
```

The changelog will be merged to:

```markdown
# Changelog

## [Unreleased]

### Added

### Changed

### Deprecated

### Removed

### Fixed

### Updated


## [0.1.0] - 2021-05-01

### Added
- `changelog` can merge non-final versions into one final version
- `readme-badges-generator` prints Kotlin version

### Fixed
- Kotlin version is found by checking all the projects instead of from Version Catalog
```
