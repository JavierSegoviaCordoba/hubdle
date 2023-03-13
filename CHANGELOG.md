# Changelog

## [Unreleased]

### Added

### Changed

### Deprecated

### Removed

### Fixed

### Updated

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

[Unreleased]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.5...HEAD

[0.5.0-alpha.5]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.4...0.5.0-alpha.5

[0.5.0-alpha.4]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.3...0.5.0-alpha.4

[0.5.0-alpha.3]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.2...0.5.0-alpha.3

[0.5.0-alpha.2]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.1...0.5.0-alpha.2

[0.5.0-alpha.1]: https://github.com/JavierSegoviaCordoba/hubdle/commits/0.5.0-alpha.1
