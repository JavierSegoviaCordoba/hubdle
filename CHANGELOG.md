# Changelog

## [Unreleased]

### Added

### Changed

### Deprecated

### Fixed

### Removed

### Updated

## [0.7.12] - 2024-09-26

### Fixed

- setting incorrect `manifest` path

## [0.7.11] - 2024-09-09

### Changed

- Settings plugin to make it more compatible with project isolation

### Fixed

- `project services has been closed` crash with Gradle 8.10.1

## [0.7.10] - 2024-09-02

### Fixed

- sonar indexed twice the same file

## [0.7.9] - 2024-09-02

### Added

- generate additional data configuration
- build konfig feature
- Atomicfu feature

### Changed

- Kotlin Multiplatform to use `applyDefaultHierarchyTemplate()`

### Fixed

- coverage excluded source sets

## [0.7.8] - 2024-08-29

### Added

- Kotlin test source sets to excludes source sets in Kover setup
- `testIntegration` to excludes source sets in Kover setup
- `testFunctional` to excludes source sets in Kover setup

## [0.7.7] - 2024-08-22

### Updated

- dependencies

## [0.7.6] - 2024-08-17

### Changed

- `PluginId` visibility to `public`
- `Semver` implementation to use `pluginManager::withPlugin`

### Fixed

- task with name `patchChangelog` not found
- task with name `dokkaHtmlMultiModule` not found

## [0.7.5] - 2024-08-15

### Fixed

- `mapVersion` signature

## [0.7.4] - 2024-08-15

### Updated

- dependencies

## [0.7.3] - 2024-08-15

### Updated

- dependencies

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

[Unreleased]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.12...HEAD

[0.7.12]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.11...p0.7.12

[0.7.11]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.10...p0.7.11

[0.7.10]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.9...p0.7.10

[0.7.9]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.8...p0.7.9

[0.7.8]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.7...p0.7.8

[0.7.7]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.6...p0.7.7

[0.7.6]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.5...p0.7.6

[0.7.5]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.4...p0.7.5

[0.7.4]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.3...p0.7.4

[0.7.3]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.2...p0.7.3

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

[0.6.1]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.6.0...p0.6.1

[0.6.0]: https://github.com/JavierSegoviaCordoba/hubdle/commits/p0.6.0
