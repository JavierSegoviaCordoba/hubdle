# Changelog

## [Unreleased]

### Added

### Changed

### Deprecated

### Removed

### Fixed

### Updated

- `io.kotest.extensions:kotest-extensions-wiremock -> 2.0.0`
- `com.javiersc.hubdle:com.javiersc.hubdle.gradle.plugin -> 0.5.0-alpha.8`

## [0.5.0-alpha.8] - 2023-03-20

### Changed

- JUnit 4 as default instead of JUnit 5

### Fixed

- testing framework missing dependency

## [0.5.0-alpha.7] - 2023-03-20

### Added

- Android Lint reports to Sonar

### Changed

- codegen libraries to use a `hubdle` version catalog

### Fixed

- `PrebuildSiteTask` uses `reduce` on empty lists

### Updated

- `com.gradle.enterprise:com.gradle.enterprise.gradle.plugin -> 3.12.5`
- `com.javiersc.semver:semver-gradle-plugin -> 0.5.0-alpha.2`
- `io.kotest.extensions:kotest-assertions-ktor -> 2.0.0`
- `com.diffplug.spotless:spotless-plugin-gradle -> 6.17.0`
- `com.javiersc.hubdle:com.javiersc.hubdle.gradle.plugin -> 0.5.0-alpha.6`

## [0.5.0-alpha.6] - 2023-03-13

### Removed

- Detekt and Kover merged reports

## [0.5.0-alpha.5] - 2023-03-13

### Fixed

- Fix Kover reports are not updated to Sonarcloud

### Updated

- `com.javiersc.hubdle:com.javiersc.hubdle.gradle.plugin -> 0.5.0-alpha.4`

## [0.5.0-alpha.4] - 2023-03-12

### Fixed

- `versioning.semver` doesn't apply the correct semver plugin id

## [0.5.0-alpha.3] - 2023-03-12

### Changed

- `versioning` includes a Hubdle `semver` extension

### Fixed

- missing inputs on `PrebuildSiteTask`

### Updated

- `gradle -> 8.0.2`
- `com.javiersc.semver:semver-gradle-plugin -> 0.5.0-alpha.1`
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

[Unreleased]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.8...HEAD

[0.5.0-alpha.8]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.7...0.5.0-alpha.8

[0.5.0-alpha.7]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.6...0.5.0-alpha.7

[0.5.0-alpha.6]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.5...0.5.0-alpha.6

[0.5.0-alpha.5]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.4...0.5.0-alpha.5

[0.5.0-alpha.4]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.3...0.5.0-alpha.4

[0.5.0-alpha.3]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.2...0.5.0-alpha.3

[0.5.0-alpha.2]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.1...0.5.0-alpha.2

[0.5.0-alpha.1]: https://github.com/JavierSegoviaCordoba/hubdle/commits/0.5.0-alpha.1
