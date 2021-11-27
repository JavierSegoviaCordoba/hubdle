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

#### Patch changelog

```shell
./gradlew patchChangelog
```

#### Merge changelog

This task merges non-final versions into final versions if possible.

```shell
./gradlew mergeChangelog
```

- Add items via CLI

It allows adding easily a new item to the any section block:

```shell
./gradlew addChangelogItem --added "some new item"
```

It supports add all dependencies from a PR created by Renovate if the body content is passed as
argument:

```shell
./gradlew addChangelogItem --renovate "[PR BODY]"
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
