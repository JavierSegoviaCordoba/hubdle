# Changelog

## [Unreleased]

### Added

### Changed

### Deprecated

### Removed

### Fixed

### Updated

## [0.5.0-alpha.38] - 2023-06-29

### Fixed

- `generateProjectData` task stack overflow error

## [0.5.0-alpha.37] - 2023-06-29

### Removed

- `compileKotlin` task depends on `generateProjectData`
- `assemble` task depends on `generateProjectData`

### Fixed

- main Kotlin source set takes `generateProjectData` as input

## [0.5.0-alpha.36] - 2023-06-29

### Changed

- `prepareKotlinIdeaImport` task depends on `generateProjectData`
- `compileKotlin` task depends on `generateProjectData`
- `assemble` task depends on `generateProjectData`

## [0.5.0-alpha.35] - 2023-06-29

### Fixed

- Hubdle Settings is not checking if catalog is enabled

## [0.5.0-alpha.34] - 2023-06-16

### Added

- `replaceStrictVersion` to Hubdle settings catalog extension

## [0.5.0-alpha.33] - 2023-06-14

### Added

- `addExtensionDependencies` to Kotlin Compiler feature

## [0.5.0-alpha.32] - 2023-06-14

### Added

- `test-data` source set to Kotlin Compiler feature
- multiple project directory and root directory paths to `GenerateProjectDataTask`

### Fixed

- `generateProjectDataTask` calculated package name
- `testProjects` aren't added as Jar tasks in Kotlin Compiler feature

## [0.5.0-alpha.31] - 2023-06-08

### Fixed

- `sonar.sources` and `sonar.tests`

## [0.5.0-alpha.30] - 2023-06-08

### Fixed

- `GenerateProjectDataTask` dependency
- `GenerateMetaRuntimeClasspathProviderTask` dependency

## [0.5.0-alpha.29] - 2023-06-08

### Added

- `catalog` extension in Settings plugin

### Changed

- removed `catalogVersion` in Settings plugin

## [0.5.0-alpha.28] - 2023-06-07

### Added

- apply JavierSC Kotlin compiler dependencies in Kotlin Compiler feature

### Fixed

- Sonar is not being skipped with Hubdle `analysis.sonar.isFullEnabled` disabled (again)

## [0.5.0-alpha.27] - 2023-06-07

### Added

- `compilerOptions` to Kotlin extension
- `testDependencies` to Kotlin compiler extension
- codegen `MetaRuntimeClasspathProvider` based on test projects
- `generateTestOnSync: Property<Boolean>` in Kotlin compiler extension
- codegen directories based on `testTypes` in Kotlin compiler extension

### Changed

- `prepareKotlinIdeaImport` dependencies based on `mainClass`
- `generateKotlinCompilerTests` is disabled if `mainClass` is blank

### Removed

- `com.javiersc.semver:semver-gradle-plugin` from hubdle catalog
- `kotlinVersion` in Kotlin compiler extension

### Fixed

- circular dependencies on `testProject` dependencies in Kotlin compiler extension

## [0.5.0-alpha.26] - 2023-06-03

### Added

- missing `detekt` and `sonar` extensions on `analysis`

### Fixed

- Sonar is not being skipped with Hubdle `analysis.sonar.isFullEnabled` disabled

## [0.5.0-alpha.25] - 2023-06-02

### Changed

- `hubdleVersionCatalogVersion` to `catalogVersion`

### Fixed

- hubdle version is not used on settings extension
- version catalog is not published

## [0.5.0-alpha.24] - 2023-05-26

### Fixed

- JUnit dependencies
- Kotlin Compiler feature JUnit dependencies

## [0.5.0-alpha.23] - 2023-05-25

### Added

- `kotlinVersion` property to Kotlin Compiler feature
- `Provider<MinimalExternalModuleDependency>.asString(): String` function
- `Provider<MinimalExternalModuleDependency>.moduleAsString(): String` function
- `Provider<MinimalExternalModuleDependency>.versionAsString(): String?` function

## [0.5.0-alpha.22] - 2023-05-25

### Added

- `testing` to `config`
- Kotlin `compiler` feature to Kotlin JVM

## [0.5.0-alpha.21] - 2023-05-22

### Fixed

- `maven-publish` plugin is applied when publishing is disabled

## [0.5.0-alpha.20] - 2023-05-22

### Fixed

- Nexus properties aren't nullable as default

## [0.5.0-alpha.19] - 2023-05-22

### Fixed

- Settings plugin doesn't change the root project name

## [0.5.0-alpha.18] - 2023-05-22

### Changed

- `intellij.publishToken` property to `intellij.token`

### Removed

- `root.project.dir.name`

### Fixed

- Kover html and xml reports path

## [0.5.0-alpha.17] - 2023-05-19

### Added

- Test Retry Gradle plugin

### Updated

- `org.jetbrains.kotlinx:kover -> 0.7.0`

## [0.5.0-alpha.16] - 2023-05-07

### Fixed

- version is checked when publishing to maven local test repo

## [0.5.0-alpha.15] - 2023-05-07

### Fixed

- `sonar.projectKey` is using `sonar.projectName`

## [0.5.0-alpha.14] - 2023-05-06

### Fixed

- Sonar is using providers instead of real values

## [0.5.0-alpha.13] - 2023-05-06

### Added

- `maxParallelForks` to `testing` extension

### Changed

- hubdle catalog to be used as a dependency instead of codegen it

## [0.5.0-alpha.12] - 2023-04-28

### Fixed

- Gradle enterprise is applied too late
- `PublishToMavenLocal` with signing plugin

## [0.5.0-alpha.11] - 2023-04-18

### Changed

- libs catalog to be merged with hubdle catalog

### Fixed

- Some publish tasks are not disabled based on the tag prefix

### Updated

- `com.javiersc.kotlin:kotlin-test-testng -> 0.1.0-alpha.13`
- `com.javiersc.kotlin:kotlin-test-junit5 -> 0.1.0-alpha.13`
- `com.javiersc.kotlin:kotlin-test-junit -> 0.1.0-alpha.13`
- `com.javiersc.kotlin:kotlin-stdlib -> 0.1.0-alpha.13`
- `com.javiersc.hubdle:com.javiersc.hubdle.gradle.plugin -> 0.5.0-alpha.10`

## [0.5.0-alpha.10] - 2023-04-16

### Added

- `build/generated/*` directories for each source set
- `org.jetbrains.kotlin:kotlin-gradle-plugin-api` to hubdle catalog

### Fixed

- emptyJavadocsJar task
- Android native source sets

## [0.5.0-alpha.9] - 2023-04-15

### Added

- `watchosDeviceArm64` target
- `androidNative` targets
- `wasm` target
- hubdle version catalog publication

### Changed

- publish and changelog tasks to be disabled based on tag prefix if semver is enabled

### Removed

- `linuxArm32Hfp` target
- `linuxMips32` target
- `linuxMipsel32` target
- `mingwX86` target
- `iosArm32` target
- `watchosX86` target
- `wasm32` target
- `com.android.application` from hubdle catalog
- `com.android.library` from hubdle catalog

### Fixed

- `jvmAndAndroid` source set

### Updated

- `com.android.tools.build:gradle -> 8.0.0`
- `gradle -> 8.1`
- `org.jetbrains.intellij.deps:intellij-coverage-agent -> 1.0.715`
- `com.gradle.enterprise:com.gradle.enterprise.gradle.plugin -> 3.13`
- `org.jetbrains.kotlin:kotlin-test-testng -> 1.8.20`
- `org.jetbrains.kotlin:kotlin-test-junit5 -> 1.8.20`
- `org.jetbrains.kotlin:kotlin-test-junit -> 1.8.20`
- `org.jetbrains.kotlin:kotlin-test-annotations-common -> 1.8.20`
- `org.jetbrains.kotlin:kotlin-test -> 1.8.20`
- `org.jetbrains.kotlin:kotlin-gradle-plugin -> 1.8.20`
- `androidx.compose.compiler:compiler -> 1.4.5`
- `org.jetbrains.kotlin:kotlin-serialization -> 1.8.20`
- `app.cash.molecule:molecule-gradle-plugin -> 0.9.0`
- `org.jetbrains.compose:compose-gradle-plugin -> 1.4.0`
- `com.gradle.publish:plugin-publish-plugin -> 1.2.0`
- `com.diffplug.spotless:spotless-plugin-gradle -> 6.18.0`
- `androidx.compose.ui:ui-tooling-preview -> 1.4.1`
- `androidx.compose.material:material-icons-extended -> 1.4.1`
- `androidx.compose.material:material -> 1.4.1`
- `androidx.compose.foundation:foundation -> 1.4.1`
- `androidx.compose.animation:animation -> 1.4.1`
- `androidx.core:core-ktx -> 1.10.0`
- `androidx.compose.ui:ui-util -> 1.4.1`
- `androidx.compose.ui:ui-tooling -> 1.4.1`
- `androidx.compose.ui:ui-test-manifest -> 1.4.1`
- `androidx.compose.ui:ui-test -> 1.4.1`
- `androidx.compose.ui:ui -> 1.4.1`
- `androidx.compose.runtime:runtime-saveable -> 1.4.1`
- `androidx.compose.runtime:runtime -> 1.4.1`
- `androidx.compose.material:material-ripple -> 1.4.1`
- `androidx.compose.material:material-icons-core -> 1.4.1`
- `androidx.compose.foundation:foundation-layout -> 1.4.1`
- `androidx.compose.animation:animation-graphics -> 1.4.1`
- `androidx.compose.animation:animation-core -> 1.4.1`
- `com.android.tools:desugar_jdk_libs -> 2.0.3`
- `app.cash.turbine:turbine -> 0.12.3`
- `org.jetbrains.intellij.plugins:gradle-intellij-plugin -> 1.13.3`
- `io.insert-koin:koin-androidx-compose -> 3.4.3`
- `io.insert-koin:koin-test-junit5 -> 3.4.0`
- `io.insert-koin:koin-test-junit4 -> 3.4.0`
- `io.insert-koin:koin-test -> 3.4.0`
- `io.insert-koin:koin-logger-slf4j -> 3.4.0`
- `io.insert-koin:koin-ktor -> 3.4.0`
- `io.insert-koin:koin-core -> 3.4.0`
- `io.insert-koin:koin-android -> 3.4.0`
- `androidx.lifecycle:lifecycle-viewmodel-compose -> 2.6.1`
- `androidx.lifecycle:lifecycle-viewmodel-ktx -> 2.6.1`
- `androidx.lifecycle:lifecycle-viewmodel -> 2.6.1`
- `androidx.activity:activity-ktx -> 1.7.0`
- `androidx.activity:activity-compose -> 1.7.0`
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

[Unreleased]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.38...HEAD

[0.5.0-alpha.38]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.37...0.5.0-alpha.38

[0.5.0-alpha.37]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.36...0.5.0-alpha.37

[0.5.0-alpha.36]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.35...0.5.0-alpha.36

[0.5.0-alpha.35]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.34...0.5.0-alpha.35

[0.5.0-alpha.34]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.33...0.5.0-alpha.34

[0.5.0-alpha.33]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.32...0.5.0-alpha.33

[0.5.0-alpha.32]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.31...0.5.0-alpha.32

[0.5.0-alpha.31]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.30...c0.5.0-alpha.31

[0.5.0-alpha.30]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.29...c0.5.0-alpha.30

[0.5.0-alpha.29]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.28...c0.5.0-alpha.29

[0.5.0-alpha.28]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.27...c0.5.0-alpha.28

[0.5.0-alpha.27]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.26...c0.5.0-alpha.27

[0.5.0-alpha.26]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.25...c0.5.0-alpha.26

[0.5.0-alpha.25]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.24...c0.5.0-alpha.25

[0.5.0-alpha.24]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.23...c0.5.0-alpha.24

[0.5.0-alpha.23]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.22...c0.5.0-alpha.23

[0.5.0-alpha.22]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.21...c0.5.0-alpha.22

[0.5.0-alpha.21]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.20...c0.5.0-alpha.21

[0.5.0-alpha.20]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.19...c0.5.0-alpha.20

[0.5.0-alpha.19]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.18...c0.5.0-alpha.19

[0.5.0-alpha.18]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.17...c0.5.0-alpha.18

[0.5.0-alpha.17]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.16...c0.5.0-alpha.17

[0.5.0-alpha.16]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.15...c0.5.0-alpha.16

[0.5.0-alpha.15]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.14...c0.5.0-alpha.15

[0.5.0-alpha.14]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.13...c0.5.0-alpha.14

[0.5.0-alpha.13]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.12...c0.5.0-alpha.13

[0.5.0-alpha.12]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.11...c0.5.0-alpha.12

[0.5.0-alpha.11]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.10...c0.5.0-alpha.11

[0.5.0-alpha.10]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.9...c0.5.0-alpha.10

[0.5.0-alpha.9]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.8...c0.5.0-alpha.9

[0.5.0-alpha.8]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.7...c0.5.0-alpha.8

[0.5.0-alpha.7]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.6...c0.5.0-alpha.7

[0.5.0-alpha.6]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.5...c0.5.0-alpha.6

[0.5.0-alpha.5]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.4...c0.5.0-alpha.5

[0.5.0-alpha.4]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.3...c0.5.0-alpha.4

[0.5.0-alpha.3]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.2...c0.5.0-alpha.3

[0.5.0-alpha.2]: https://github.com/JavierSegoviaCordoba/hubdle/compare/c0.5.0-alpha.1...c0.5.0-alpha.2

[0.5.0-alpha.1]: https://github.com/JavierSegoviaCordoba/hubdle/commits/c0.5.0-alpha.1
