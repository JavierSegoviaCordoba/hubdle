# Changelog

## [Unreleased]

### Added

### Changed

### Deprecated

### Fixed

### Removed

### Updated

## [0.7.2] - 2024-08-03

### Fixed

- source sets configuration being too late

## [0.7.1] - 2024-07-25

### Updated

- Hubdle catalog

## [0.7.0] - 2024-05-30

### Updated

- Kotlin to 2.0.0

## [0.6.8] - 2024-04-19

### Changed

- JetBrains compiler to Android compiler in `HubdleKotlinComposeFeatureExtension`

### Fixed

- `buildScan::publishAlways` is incompatible with configuration cache

## [0.6.7] - 2024-04-18

### Changed

- `com.gradle.enterprise:com.gradle.enterprise.gradle.plugin -> com.gradle:com.gradle.develocity-gradle-plugin`
- allow publishing with non Kotlin dev versions

## [0.6.6] - 2024-04-09

### Updated

- dependencies

## [0.6.5] - 2024-03-28

### Added

- build directory to `sonar.exclusions` property

## [0.6.4] - 2024-03-26

### Added

- `org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI` opt in Compiler feature 

### Fixed

- `AddChangelogItemTask` fails if there is no version in the changelog

### Removed

- priorities

## [0.6.3] - 2024-02-13

### Added

- `withPlugin` function to `hubdle`
- `withPlugins` function to `hubdle`
- `generate` lifecycle task

### Changed

- Compiler tasks dependencies
- Android version to be not `strictly`
- Kotlin version to be not `strictly`

### Fixed

- `MergeChangelogTask` adds the same updated dependency multiple times with different versions

## [0.6.2] - 2024-02-05

### Changed

- tag prefix to `p` for Hubdle Gradle plugin

### Fixed

- `generateProjectData` task not being wired with the Kotlin source set

## [0.6.1] - 2024-02-04

### Fixed

- accessing to `publishPlugins` when it is not registered

## [0.6.0] - 2024-02-04

### Added

- `tests` task to run all `Test` tasks
- `fixChecks` task
- `applyFormat` depends on `fixChecks` task
- `dumpApi` depends on `fixChecks` task

### Removed

- `allTests` task

[Unreleased]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.2...HEAD

[0.7.2]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.1...p0.7.2

[0.7.1]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.0...p0.7.1

[0.7.0]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.6.8...p0.7.0

[0.6.8]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.6.7...p0.6.8

[0.6.7]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.6.6...p0.6.7

[0.6.6]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.6.5...p0.6.6

[0.6.5]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.6.4...p0.6.5

[0.6.4]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.6.3...p0.6.4

[0.6.3]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.6.2...p0.6.3

[0.6.2]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.6.1...p0.6.2

[0.6.1]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.6.0...0.6.1

[0.6.0]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-beta.16...0.6.0
