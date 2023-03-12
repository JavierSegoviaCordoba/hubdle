# Changelog

## [Unreleased]

### Added

### Changed

### Deprecated

### Removed

### Fixed

- missing inputs on `PrebuildSiteTask`

### Updated

- `org.jetbrains.kotlin:kotlin-test-junit5 -> 1.8.10`
- `org.jetbrains.kotlin:kotlin-test-annotations-common -> 1.8.10`
- `org.jetbrains.kotlin:kotlin-test -> 1.8.10`
- `org.jetbrains.kotlinx:kotlinx-coroutines-test -> 1.6.4`
- `org.jetbrains.kotlinx:kotlinx-coroutines-core -> 1.6.4`
- `org.jetbrains.kotlinx:kotlinx-coroutines-android -> 1.6.4`
- `com.javiersc.hubdle:com.javiersc.hubdle.gradle.plugin -> 0.5.0-alpha.2`

## [0.5.0-alpha.2] - 2023-03-11

### Fixed

- `publishAlways` doesn't check for `true` or `false`

## [0.5.0-alpha.1] - 2023-03-11

### Added

- `useOnAllProjects` to `hubdleSettings` extension
- `buildScan` to `hubdleSettings` extension

### Changed

- `site` shows Qodana and Sonar in the navigation bar
- `site` doesn't upload reports to GitHub Pages

### Fixed

- `PublishToMavenRepository` tasks must run after `Sign` tasks

### Updated

- `org.jetbrains.intellij.plugins:gradle-intellij-plugin -> 1.13.2`
- `app.cash.molecule:molecule-gradle-plugin -> 0.8.0`

[Unreleased]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.2...HEAD

[0.5.0-alpha.2]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.1...0.5.0-alpha.2

[0.5.0-alpha.1]: https://github.com/JavierSegoviaCordoba/hubdle/commits/0.5.0-alpha.1
