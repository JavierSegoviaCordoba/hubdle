# Changelog

## [Unreleased]

### Added

### Changed

### Deprecated

### Removed

### Fixed

- `testIntegration` source set and `integrationTest` task

### Updated

## [0.4.0-alpha.5] - 2023-01-26

### Changed

- `extendedGradle` affects `testFunctional`, `testIntegration`, and `testFixtures` source sets

### Fixed

- `HubdleGradlePluginFeatureExtension` tests source sets

### Updated

- `com.javiersc.semver:semver-core -> 0.1.0-beta.13`

## [0.4.0-alpha.4] - 2023-01-25

### Added

- `extendedGradle` to `HubdleGradlePluginFeatureExtension`
- `javiersc-semver-core` to Hubdle catalog

## [0.4.0-alpha.3] - 2023-01-25

### Fixed

- `HubdleGradlePluginFeatureExtension` duplicates Maven Publication

## [0.4.0-alpha.2] - 2023-01-25

### Fixed

- `HubdleKotlinJvmExtension` not applying `java-test-fixtures` when `isTestFixturesEnabled` is `true`
- `HubdleGradlePluginFeatureExtension` not being configured

## [0.4.0-alpha.1] - 2023-01-25

### Changed

- `kotlin/gradle` extension to `kotlin/jvm/features/gradle`
- `kotlin/intellij` extension to `kotlin/jvm/features/intellij`

### Updated

- `androidx.compose.ui:ui-util -> 1.3.3`
- `androidx.compose.ui:ui-tooling-preview -> 1.3.3`
- `androidx.compose.ui:ui-tooling -> 1.3.3`
- `androidx.compose.ui:ui-test-manifest -> 1.3.3`
- `androidx.compose.ui:ui-test -> 1.3.3`
- `androidx.compose.ui:ui -> 1.3.3`
- `androidx.compose.runtime:runtime-saveable -> 1.3.3`
- `androidx.compose.runtime:runtime -> 1.3.3`
- `androidx.compose.compiler:compiler -> 1.4.0`
- `androidx.compose.animation:animation-graphics -> 1.3.3`
- `androidx.compose.animation:animation-core -> 1.3.3`
- `androidx.compose.animation:animation -> 1.3.3`
- `org.jetbrains.kotlin:kotlin-test-testng -> 1.8.0`
- `org.jetbrains.kotlin:kotlin-test-junit5 -> 1.8.0`
- `org.jetbrains.kotlin:kotlin-test-junit -> 1.8.0`
- `org.jetbrains.kotlin:kotlin-test-annotations-common -> 1.8.0`
- `org.jetbrains.kotlin:kotlin-test -> 1.8.0`
- `org.jetbrains.kotlin:kotlin-gradle-plugin -> 1.8.0`
- `org.jetbrains.kotlin:kotlin-serialization -> 1.8.0`
- `org.jetbrains.intellij.plugins:gradle-intellij-plugin -> 1.12.0`
- `androidx.appcompat:appcompat -> 1.6.0`
- `com.android.tools.build:gradle -> 7.4.0`
- `com.android.library:com.android.library.gradle.plugin -> 7.4.0`
- `com.android.application:com.android.application.gradle.plugin -> 7.4.0`
- `io.arrow-kt:arrow-core -> 1.1.5`
- `com.diffplug.spotless:spotless-plugin-gradle -> 6.13.0`
- `org.junit.jupiter:junit-jupiter-params -> 5.9.2`
- `org.junit.jupiter:junit-jupiter-api -> 5.9.2`

## [0.3.0-alpha.4] - 2023-01-09

### Updated

- `com.javiersc.semver:semver-gradle-plugin -> 0.4.0-alpha.1`

## [0.3.0-alpha.3] - 2023-01-09

### Changed

- `testing` extension doesn't depends on Kotlin now

## [0.3.0-alpha.2] - 2023-01-09

### Fixed

- Fix `build` badge codegen in Readme Badges extension
- Fix Kotlin Multiplatform extensions

### Updated

- `actions/cache -> v3.2.3`

## [0.3.0-alpha.1] - 2023-01-09

### Changed

- `hubdle-gradle-plugin` with a big refactor,
  check [hubdle extension overview](.docs/docs/extensions/HUBDLE_EXTENSION_OVERVIEW.md)

### Updated

- `actions/checkout -> v3.3.0`
- `com.squareup.okio:okio -> 3.3.0`
- `com.diffplug.spotless:spotless-plugin-gradle -> 6.12.1`
- `io.ktor:ktor-serialization-kotlinx-json -> 2.2.2`
- `io.ktor:ktor-client-okhttp -> 2.2.2`
- `io.ktor:ktor-client-mock -> 2.2.2`
- `io.ktor:ktor-client-core -> 2.2.2`
- `io.ktor:ktor-client-content-negotiation -> 2.2.2`
- `io.ktor:ktor-client-cio -> 2.2.2`
- `com.gradle.enterprise:com.gradle.enterprise.gradle.plugin -> 3.12.2`
- `org.jetbrains.compose:compose-gradle-plugin -> 1.2.2`
- `actions/cache -> v3.2.2`
- `com.squareup.okhttp3:okhttp -> 5.0.0-alpha.11`
- `com.squareup.okhttp3:mockwebserver3-junit5 -> 5.0.0-alpha.11`
- `com.squareup.okhttp3:mockwebserver3-junit4 -> 5.0.0-alpha.11`
- `com.squareup.okhttp3:mockwebserver -> 5.0.0-alpha.11`
- `org.jetbrains.intellij.plugins:gradle-intellij-plugin -> 1.11.0`
- `actions/setup-java -> v3.9.0`
- `com.facebook:ktfmt -> 0.42`
- `androidx.compose.ui:ui-util -> 1.3.2`
- `androidx.compose.ui:ui-tooling-preview -> 1.3.2`
- `androidx.compose.ui:ui-tooling -> 1.3.2`
- `androidx.compose.ui:ui-test-manifest -> 1.3.2`
- `androidx.compose.ui:ui-test -> 1.3.2`
- `androidx.compose.ui:ui -> 1.3.2`
- `androidx.compose.runtime:runtime-saveable -> 1.3.2`
- `androidx.compose.runtime:runtime -> 1.3.2`
- `androidx.compose.animation:animation-graphics -> 1.3.2`
- `androidx.compose.animation:animation-core -> 1.3.2`
- `androidx.compose.animation:animation -> 1.3.2`
- `org.eclipse.jgit:org.eclipse.jgit -> 6.4.0.202211300538-r`
- `com.javiersc.hubdle:com.javiersc.hubdle.gradle.plugin -> 0.2.0-alpha.46`

[Unreleased]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.4.0-alpha.5...HEAD

[0.4.0-alpha.5]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.4.0-alpha.4...0.4.0-alpha.5

[0.4.0-alpha.4]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.4.0-alpha.3...0.4.0-alpha.4

[0.4.0-alpha.3]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.4.0-alpha.2...0.4.0-alpha.3

[0.4.0-alpha.2]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.4.0-alpha.1...0.4.0-alpha.2

[0.4.0-alpha.1]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.3.0-alpha.4...0.4.0-alpha.1

[0.3.0-alpha.4]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.3.0-alpha.3...0.3.0-alpha.4

[0.3.0-alpha.3]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.3.0-alpha.2...0.3.0-alpha.3

[0.3.0-alpha.2]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.3.0-alpha.1...0.3.0-alpha.2

[0.3.0-alpha.1]: https://github.com/JavierSegoviaCordoba/hubdle/commits/0.3.0-alpha.1
