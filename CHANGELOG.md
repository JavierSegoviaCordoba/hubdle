# Changelog

## [Unreleased]

### Added

- `build/generated/*` directories for each source set
- `org.jetbrains.kotlin:kotlin-gradle-plugin-api` to hubdle catalog

### Changed

### Deprecated

### Removed

### Fixed

- emptyJavadocsJar task
- Android native source sets

### Updated

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

[Unreleased]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.9...HEAD

[0.5.0-alpha.9]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.8...0.5.0-alpha.9

[0.5.0-alpha.8]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.7...0.5.0-alpha.8

[0.5.0-alpha.7]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.6...0.5.0-alpha.7

[0.5.0-alpha.6]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.5...0.5.0-alpha.6

[0.5.0-alpha.5]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.4...0.5.0-alpha.5

[0.5.0-alpha.4]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.3...0.5.0-alpha.4

[0.5.0-alpha.3]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.2...0.5.0-alpha.3

[0.5.0-alpha.2]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-alpha.1...0.5.0-alpha.2

[0.5.0-alpha.1]: https://github.com/JavierSegoviaCordoba/hubdle/commits/0.5.0-alpha.1
