# Changelog

## [Unreleased]

### Added

### Changed

### Deprecated

### Fixed

- `functionalTest` and `integrationTest` configurations
- JVM version not applied totally via `jvmVersion`

### Removed

### Updated

## [0.14.0] - 2025-07-05

### Changed

- Gradle feature to its own `gradle` dsl outside of `kotlin` or `java`

### Fixed

- Gradle plugin feature crashing due to missing `maven-publish` plugin

### Updated

- dependencies

## [0.13.0] - 2025-06-19

### Fixed

- issues setting the `JvmTarget`

## [0.12.0] - 2025-06-18

### Added

- Vanniktech Maven Publish Gradle plugin

### Removed

- Nexus plugin

## [0.11.0] - 2025-06-15

### Added

- `hubdle::config::languageSettings::enableLanguageFeatures`

### Removed

- `kotlin::*::features::contextReceivers`

### Updated

- dependencies

## [0.10.0] - 2025-06-15

### Changed

- Nexus extension to use the new Maven Central urls by default

## [0.9.1] - 2025-06-14

### Updated

- dependencies

## [0.9.0] - 2025-03-20

### Updated

- dependencies

## [0.8.7] - 2025-02-24

### Fixed

- JetBrains Kotlin test jvm dependencies added to non jvm source sets

## [0.8.6] - 2025-02-23

### Added

- `com.javiersc.kotlin:kotlin-test` assertions to Kotlin Power Assert
- `hubdle.repositories.jetbrains.compose.dev` boolean properties
- `hubdle.repositories.jetbrains.kotlin.bootstrap` boolean properties
- `hubdle.repositories.jetbrains.kotlin.dev` boolean properties
- `hubdle.repositories.sonatype.snapshots` boolean properties
- `hubdle.repositories.mavenLocal` boolean properties

### Fixed

- `kotlin-test` not added to Test Fixtures source set

## [0.8.5] - 2025-02-22

### Added

- `com.javiersc.kotlin:kotlin-test` dependency

## [0.8.4] - 2025-02-19

### Changed

- Kotest disabled by default

### Fixed

- `kotlin` source set in `JavaPluginExtension` with Kotlin 2.1.20-RC+

## [0.8.3] - 2025-02-11

### Added

- `WAsm > JS > browser` target
- `WAsm > JS > d8` target
- `WAsm > JS > nodejs` target
- `WAsm > WAsi > nodejs` target

### Changed

- `wasm` extension now has `js` and `wasi` extensions

### Fixed

- Power Assert adding non-test source sets

### Removed

- Top level `wasmJs` extension inside `multiplatform` extension

## [0.8.2] - 2025-01-27

### Added

- Kotlin Power Assert feature

## [0.8.1] - 2024-11-28

### Changed

- `compileSdk` to `35`
- `targetSdk` to `35`

## [0.8.0] - 2024-11-27

### Updated

- dependencies

## [0.7.18] - 2024-10-13

### Fixed

- Sonar crash `File can't be indexed twice`

## [0.7.17] - 2024-10-13

### Fixed

- Kotlin configuration on Sonar

## [0.7.16] - 2024-10-12

### Fixed

- Sonar task crashes

## [0.7.15] - 2024-10-12

### Changed

- `ValidatePlugins::enableStricterValidation` to `true`

### Fixed

- Sonar is missing some Kotlin directories

## [0.7.14] - 2024-10-10

### Updated

- dependencies

## [0.7.13] - 2024-10-09

### Added

- `HubdleConfigPublishingGradlePortalExtension` to disable publishing to Gradle portal

### Updated

- dependencies

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

[Unreleased]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.14.0...HEAD

[0.14.0]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.13.0...p0.14.0

[0.13.0]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.12.0...p0.13.0

[0.12.0]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.11.0...p0.12.0

[0.11.0]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.10.0...p0.11.0

[0.10.0]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.9.1...p0.10.0

[0.9.1]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.9.0...p0.9.1

[0.9.0]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.8.7...p0.9.0

[0.8.7]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.8.6...p0.8.7

[0.8.6]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.8.5...p0.8.6

[0.8.5]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.8.4...p0.8.5

[0.8.4]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.8.3...p0.8.4

[0.8.3]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.8.2...p0.8.3

[0.8.2]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.8.1...p0.8.2

[0.8.1]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.8.0...p0.8.1

[0.8.0]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.18...p0.8.0

[0.7.18]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.17...p0.7.18

[0.7.17]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.16...p0.7.17

[0.7.16]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.15...p0.7.16

[0.7.15]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.14...p0.7.15

[0.7.14]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.13...p0.7.14

[0.7.13]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.7.12...p0.7.13

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
