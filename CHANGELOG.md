# Changelog

## [Unreleased]

### Added

### Changed

### Deprecated

### Removed

### Fixed

### Updated

- `org.eclipse.jgit:org.eclipse.jgit -> 6.3.0.202209071007-r`
- `androidx.appcompat:appcompat -> 1.5.1`
- `org.jetbrains.kotlinx:binary-compatibility-validator -> 0.11.1`
- `androidx.core:core-ktx -> 1.9.0`
- `com.squareup.moshi:moshi -> 1.14.0`
- `io.ktor:ktor-serialization-kotlinx-json -> 2.1.1`
- `io.ktor:ktor-client-okhttp -> 2.1.1`
- `io.ktor:ktor-client-mock -> 2.1.1`
- `io.ktor:ktor-client-core -> 2.1.1`
- `io.ktor:ktor-client-content-negotiation -> 2.1.1`
- `io.ktor:ktor-client-cio -> 2.1.1`
- `org.jetbrains.intellij.plugins:gradle-intellij-plugin -> 1.9.0`
- `org.jetbrains.compose:compose-gradle-plugin -> 1.2.0-alpha01-dev774`
- `org.jetbrains.kotlinx:kover -> 0.6.0`
- `com.diffplug.spotless:spotless-plugin-gradle -> 6.10.0`
- `gradle/gradle-build-action -> v2.2.5`
- `org.jetbrains.kotlinx:kotlinx-serialization-json -> 1.4.0`
- `org.jetbrains.kotlinx:kotlinx-serialization-core -> 1.4.0`
- `io.kotest:kotest-runner-junit5 -> 5.4.2`
- `io.kotest:kotest-runner-junit4 -> 5.4.2`
- `io.kotest:kotest-property -> 5.4.2`
- `io.kotest:kotest-assertions-sql -> 5.4.2`
- `io.kotest:kotest-assertions-json -> 5.4.2`
- `io.kotest:kotest-assertions-core -> 5.4.2`
- `com.gradle.enterprise:com.gradle.enterprise.gradle.plugin -> 3.11.1`
- `gradle -> 7.5.1`
- `com.android.tools.build:gradle -> 7.2.2`
- `io.kotest.extensions:kotest-extensions-testcontainers -> 1.3.4`
- `app.cash.turbine:turbine -> 0.9.0`
- `io.kotest.extensions:kotest-extensions-spring -> 1.1.2`
- `org.jetbrains.dokka:dokka-gradle-plugin -> 1.7.10`
- `org.jetbrains.kotlin:kotlin-gradle-plugin -> 1.7.10`
- `androidx.activity:activity-ktx -> 1.5.1`
- `androidx.activity:activity-compose -> 1.5.1`

## [0.2.0-alpha.33] - 2022-07-26

### Changed

- `InstallPreCommitTask` header text

### Fixed

- `com.javiersc.mokoki:mokoki-core -> com.javiersc.mokoki:mokoki`

### Updated

- `io.kotest:kotest-property -> 5.4.0`
- `io.kotest:kotest-assertions-core -> 5.4.0`

## [0.2.0-alpha.32] - 2022-07-22

### Added

- `com.squareup.okhttp3:mockwebserver3-junit4` dependency
- `com.squareup.okhttp3:mockwebserver3-junit5` dependency

### Fixed

- `ktor` version

## [0.2.0-alpha.31] - 2022-07-20

### Added

- `javierscKotlinxCoroutinesRunBlocking()` dependencies
- `javierscKotlinxCoroutinesRunBlockingAll()` dependencies

### Fixed

- `ReadmeBadgesExtension` is not using the real `projectKey` property if it exists

## [0.2.0-alpha.30] - 2022-07-20

### Added

- `publishToMavenLocalTest` and `publishToMavenLocalBuildTest` tasks if the repositories exist
- `mavenLocalTest` and `mavenLocalBuildTest` repositories
- `Hubdle` prefix to all config extension classes
- `repositories` to `HubdlePublishingExtension`

### Fixed

- `publishAllPublicationsToMavenLocalTestRepository` is signed
- being able to sign non semver artifacts

### Updated

- `gradle/gradle-build-action -> v2.2.2`

## [0.2.0-alpha.29] - 2022-07-19

### Fixed

- `signing.isRequired` is always `true` even without `publish` tasks (second try)

## [0.2.0-alpha.28] - 2022-07-18

### Fixed

- duplicate source sets in Sonarqube configuration
- `signing.isRequired` is always `true` even without `publish` tasks

## [0.2.0-alpha.27] - 2022-07-17

### Added

- `compose` feature to Android Application
- `compose` feature to Multiplatform

### Changed

- all extension has prefix `Hubdle`

### Updated

- `io.gitlab.arturbosch.detekt:detekt-gradle-plugin -> 1.21.0`

## [0.2.0-alpha.26] - 2022-07-16

### Added

- Android `buildFeatures` to `application`, `library`, and `multiplatform.android`

### Updated

- `com.facebook:ktfmt -> 0.39`
- `org.jetbrains.kotlinx:binary-compatibility-validator -> 0.11.0`
- `gradle -> 7.5`
- `org.gradle.kotlin:gradle-kotlin-dsl-plugins -> 2.4.1`

## [0.2.0-alpha.25] - 2022-07-15

### Added

- autocalculate Android `namespace`
- `enableAll()` to `ios`, `linux` `macos`, `mingw`, `native`, `tvos`, `wasm`, `watchos`
- `javiersc:kotlin-test-junit`, `junit5` and `testng` to `extendedTesting`

## [0.2.0-alpha.24] - 2022-07-14

### Added

- `kotlinTestAnnotationsCommon` and `kotlinTestJunit5` dependencies

### Changed

- dependency function doesn't use `implementation` under the hood

### Fixed

- Hierarchical project structure in Kotlin Multiplatform

## [0.2.0-alpha.23] - 2022-07-13

### Fixed

- `watchos/test` source set using `main` source set

## [0.2.0-alpha.22] - 2022-07-13

### Added

- `darwin` source set to Kotlin Multiplatform

### Changed

- Kotlin Multiplatform source set format from `commonName` to `common/name`

### Fixed

- linux test source sets in Multiplatform depending on main source sets

### Updated

- `org.jetbrains.kotlinx:kotlinx-coroutines-test -> 1.6.4`
- `org.jetbrains.kotlinx:kotlinx-coroutines-core -> 1.6.4`
- `org.jetbrains.kotlinx:kotlinx-coroutines-android -> 1.6.4`

## [0.2.0-alpha.21] - 2022-07-12

### Added

- `application` to Android extension
- `jvmAndAndroid` source dirs to Multiplatform extension
- `spotlessPredeclare` to `FormatExtension.RawConfigExtension`
- `experimentalSerializationApi` to `LanguageSettingsExtension`

### Changed

- Spotless predeclares dependencies
- `root.project.name` property is not mandatory in Hubdle Settings

### Fixed

- Android source sets in non KMP 
- Spotless is not applied without Kotlin
- `languageSettings.optIn` function

### Updated

- `actions/setup-java -> v3.4.1`
- `com.gradle.enterprise:com.gradle.enterprise.gradle.plugin -> 3.10.3`
- `com.javiersc.kotlin:kotlin-stdlib -> 0.1.0-alpha.8`

## [0.2.0-alpha.20] - 2022-07-10

### Updated

- `com.javiersc.semver:semver-gradle-plugin -> 0.3.0-alpha.4`

## [0.2.0-alpha.19] - 2022-07-09

### Updated

- `com.javiersc.semver:semver-gradle-plugin -> 0.3.0-alpha.2`

## [0.2.0-alpha.18] - 2022-07-09

### Added

- `google()` repository to Hubdle Settings plugin

### Changed

- dependencies don't need to be imported

## [0.2.0-alpha.17] - 2022-07-09

### Fixed

- DSL scope violation in every dependency (second try)

## [0.2.0-alpha.16] - 2022-07-09

### Fixed

- DSL scope violation in every dependency

## [0.2.0-alpha.15] - 2022-07-09

### Added

- `intellij` extension to Kotlin, so it is possible to create IntelliJ plugins
- `KotlinJsBrowserDsl` and `KotlinJsNodeDsl` actions to Multiplatform Javascript target

## [0.2.0-alpha.14] - 2022-07-08

### Added

- `serialization` feature to `AndroidLibrary`, `KotlinJvm` and `KotlinMultiplatform`
- `optIn` functions to `LanguageSettingsExtension`
- more dependencies to `hubdle.libs.versions.toml`
- `experimentalContracts` to `LanguageSettingsExtension`

### Removed

- `@HubdleDslMarker` from every `RawConfigExtension` function

## [0.2.0-alpha.13] - 2022-07-07

### Changed

- `java-gradle-plugin` and `kotlin("jvm")` with `kotlin-dsl` in `KotlinGradleExtension`

## [0.2.0-alpha.12] - 2022-07-06

### Added

- `excludes` function to exclude the generation of API docs in those projects
- `gradlePlugin` to `KotlinGradlePluginExtension`
- `pluginUnderTestDependencies` to `KotlinGradlePluginExtension`

## [0.2.0-alpha.11] - 2022-07-06

### Fixed

- applying signing plugin too early and with incorrect conditions

## [0.2.0-alpha.10] - 2022-07-06

### Changed

- plugin dependencies from `implementation` to `api`

## [0.2.0-alpha.9] - 2022-07-06

### Changed

- Format includes/excludes use specific source sets
- Provided dependencies are not null 

### Fixed

- AutoInclude adding incorrect included builds and projects
- Applying necessary plugins too late 

### Updated

- `io.kotest:kotest-assertions-core -> 5.3.2`
- `org.jetbrains.kotlinx:kotlinx-coroutines-core -> 1.6.3`
- `com.diffplug.spotless:spotless-plugin-gradle -> 6.8.0`
- `gradle/gradle-build-action -> v2.2.1`
- `actions/setup-java -> v3.4.0`
- `ru.vyarus:gradle-mkdocs-plugin -> 2.4.0`
- `org.sonarsource.scanner.gradle:sonarqube-gradle-plugin -> 3.4.0.2513`
- `org.jetbrains.kotlinx:binary-compatibility-validator -> 0.10.1`
- `com.javiersc.kotlin:kotlin-stdlib -> 0.1.0-alpha.7`
- `org.eclipse.jgit:org.eclipse.jgit -> 6.2.0.202206071550-r`
- `com.javiersc.gradle:gradle-test-extensions -> 1.0.0-alpha.26`
- `com.javiersc.gradle:gradle-extensions -> 1.0.0-alpha.26`

## [0.2.0-alpha.8] - 2022-07-05

### Fixed

- Multiplatform publication hasn't an empty javadoc
- Format default includes/excludes can be empty

## [0.2.0-alpha.7] - 2022-07-04

### Fixed

- crash if signing properties are not set

## [0.2.0-alpha.6] - 2022-07-04

### Fixed

- private key can't be read on Mac

## [0.2.0-alpha.5] - 2022-07-04

### Fixed

- changelog has unspecified version

## [0.2.0-alpha.4] - 2022-07-04

### Added

- ignore repositories if they are added by a project by Hubdle Settings
- `RepositoryHandler.sonatypeSnapshot` function by Hubdle Settings

### Fixed

- a crash when both, project and settings plugin are applied

## [0.2.0-alpha.3] - 2022-07-03

### Updated

- `com.javiersc.semver:semver-gradle-plugin -> 0.3.0-alpha.1`

## [0.2.0-alpha.2] - 2022-07-03

### Added

- `com.javiersc.hubdle.settings` plugin

### Fixed

- a feature can add a dependency which can be the project itself

### Updated

- `com.gradle.publish:plugin-publish-plugin -> 1.0.0`

## [0.2.0-alpha.1] - 2022-07-02

### Added

- `hubdle` extension to configure everything without applying convention plugins

### Changed

- Huge refactor, check the site [hubdle.javiersc.com](https://hubdle.javiersc.com)

### Removed

- All convention plugins to use `com.javiersc.hubdle`

## [0.1.0-rc.45] - 2022-06-18

### Updated

- `com.javiersc.semver:semver-gradle-plugin -> 0.2.0-alpha.2`
- `com.javiersc.kotlin:kotlin-stdlib -> 0.1.0-alpha.5`

## [0.1.0-rc.44] - 2022-06-15

### Added

- Configuration cache support to `all-projects`
- Build cache support to `all-projects`
- `docs` uses IDEA Gradle plugin to trigger `preBuildDocs`
- Build cache to `preBuildDocs` task in the `docs` plugin
- Configuration cache support to `buildReadmeBadges` and `build-readme-badges-generator`

### Fixed

- Install pre-commit tasks does not add `"#!/bin/bash"` at the top

### Updated

- `com.javiersc.semver:semver-gradle-plugin -> 0.2.0-alpha.1`
- `gradle/gradle-build-action -> v2.2.0`
- `org.jetbrains.kotlinx:kotlinx-coroutines-core -> 1.6.2`
- `org.jetbrains.kotlinx:binary-compatibility-validator -> 0.10.0`
- `org.jsoup:jsoup -> 1.15.1`
- `com.diffplug.spotless:spotless-plugin-gradle -> 6.6.1`

## [0.1.0-rc.43] - 2022-05-11

### Fixed

- POM license name and url are incorrect

### Updated

- `com.facebook:ktfmt -> 0.37`
- `com.diffplug.spotless:spotless-plugin-gradle -> 6.6.0`
- `actions/setup-java -> v3.3.0`
- `io.kotest:kotest-assertions-core -> 5.3.0`
- `org.jetbrains.dokka:dokka-gradle-plugin -> 1.6.21`
- `actions/checkout -> v3.0.2`
- `org.jetbrains.kotlin:kotlin-gradle-plugin -> 1.6.21`
- `org.jetbrains.kotlinx:binary-compatibility-validator -> 0.9.0`
- `io.gitlab.arturbosch.detekt:detekt-gradle-plugin -> 1.20.0`
- `org.jetbrains.kotlinx:kotlinx-coroutines-core -> 1.6.1`
- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.42`

## [0.1.0-rc.42] - 2022-04-02

### Updated

- `gradle -> 7.4.2`
- `io.kotest:kotest-assertions-core -> 5.2.2`
- `org.jetbrains.kotlin:kotlin-gradle-plugin -> 1.6.20`
- `com.gradle.publish:plugin-publish-plugin -> 0.21.0`
- `actions/setup-java -> v3.1.0`
- `com.diffplug.spotless:spotless-plugin-gradle -> 6.4.1`
- `ru.vyarus:gradle-mkdocs-plugin -> 2.3.0`
- `gradle/gradle-build-action -> v2.1.5`
- `com.facebook:ktfmt -> 0.35`
- `org.eclipse.jgit:org.eclipse.jgit -> 6.1.0.202203080745-r`
- `actions/checkout -> v3.0.0`
- `com.adarshr:gradle-test-logger-plugin -> 3.2.0`
- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.41`

## [0.1.0-rc.41] - 2022-02-25

### Fixed

- `changelog` removes incorrect update

### Updated

- `actions/setup-java -> v3.0.0`
- `com.facebook:ktfmt -> 0.33`
- `com.google.code.gson:gson -> 2.9.0`
- `com.diffplug.spotless:spotless-plugin-gradle -> 6.3.0`
- `gradle -> 7.4`
- `com.github.ben-manes:gradle-versions-plugin -> 0.42.0`
- `gradle/gradle-build-action -> v2.1.3`
- `org.jetbrains.kotlinx:kover -> 0.5.0`
- `io.kotest:kotest-assertions-core -> 5.1.0`
- `com.gradle.publish:plugin-publish-plugin -> 0.20.0`
- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.40`

## [0.1.0-rc.40] - 2022-01-14

### Added

- install tasks for pre-commits to `all-projects`

### Updated

- `com.diffplug.spotless:spotless-plugin-gradle -> 6.2.0`
- `org.jetbrains.kotlinx:kover -> 0.5.0-RC2`
- `com.javiersc.kotlin:kotlin-stdlib -> 0.1.0-alpha.4`
- `com.github.ben-manes:gradle-versions-plugin -> 0.41.0`
- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.39`

## [0.1.0-rc.39] - 2022-01-02

### Fixed

- `all-projects` uses `useJUnitPlatform()` in Android projects instead of `useJUnit()`

### Updated

- `com.github.ben-manes:gradle-versions-plugin -> 0.40.0`
- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.38`

## [0.1.0-rc.38] - 2022-01-02

### Fixed

- `allTests` does not generate reports if it is run by `build` or `check`

### Updated

- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.37`

## [0.1.0-rc.37] - 2022-01-02

### Changed

- `check` depends on `allTests`

## [0.1.0-rc.36] - 2022-01-01

### Fixed

- `docs` tasks dependencies

### Updated

- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.35`

## [0.1.0-rc.35] - 2022-01-01

### Fixed

- `docs` does not generate `api` directory

### Updated

- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.34`

## [0.1.0-rc.34] - 2021-12-31

### Added

- `readmeBadges` extension to `readme-badges-generator` to enable/disable badges
- `docs/navigation/reports` extensions to `docs` to enable/disable reports

### Fixed

- running `test` task runs all projects `test` tasks

### Updated

- `com.gradle.publish:plugin-publish-plugin -> 0.19.0`
- `org.jetbrains.dokka:dokka-gradle-plugin -> 1.6.10`
- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.33`

## [0.1.0-rc.33] - 2021-12-27

### Added

- `koverReport` task to `allTests` task

### Fixed

- `readme-badges-generator` analysis badges

## [0.1.0-rc.32] - 2021-12-26

### Added

- `sonar.login` can be passed as `SONAR_TOKEN` environment variable

## [0.1.0-rc.31] - 2021-12-26

### Added

- detekt support to Sonar implementation in `code-analysis`

### Changed

- enable detekt xml report

## [0.1.0-rc.30] - 2021-12-26

### Fixed

- `testReport` task changes project group instead of task group

## [0.1.0-rc.29] - 2021-12-26

### Added

- Code coverage section to `docs`
- `code-coverage`
- Sonarqube to `code-analysis`

## [0.1.0-rc.28] - 2021-12-25

### Fixed

- wrong path in reports iframe

## [0.1.0-rc.27] - 2021-12-25

### Changed

- `docs` reports are embedded using an iframe

### Updated

- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.26`

## [0.1.0-rc.26] - 2021-12-25

### Added

- `docs` Reports section in navigation
- `all-projects` supports aggregate tests report in the root project
- `kotlin-config` supports `com.android.application`

### Changed

- `kotlin-library` name to `kotlin-config`

### Updated

- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.25`

## [0.1.0-rc.25] - 2021-12-24

### Added

- `all-projects` register `allTests` if it doesn't exist

### Updated

- `com.diffplug.spotless:spotless-plugin-gradle -> 6.1.0`
- `gradle -> 7.3.3`
- `org.jetbrains.kotlinx:kotlinx-coroutines-core -> 1.6.0`
- `com.gradle.enterprise:com.gradle.enterprise.gradle.plugin -> 3.8`
- `com.javiersc.semver:semver-gradle-plugin -> 0.1.0-alpha.10`
- `com.javiersc.semver:semver-core -> 0.1.0-beta.10`
- `io.kotest:kotest-assertions-core -> 5.0.3`
- `org.jetbrains.kotlin:kotlin-gradle-plugin -> 1.6.10`

## [0.1.0-rc.24] - 2021-12-11

### Changed

- `spotless` downgrades version to 6.0.2
- `code-formatter` can be only applied in the root project

## [0.1.0-rc.23] - 2021-12-11

### Updated

- `io.kotest:kotest-assertions-core -> 5.0.2`
- `com.javiersc.semver:semver-gradle-plugin -> 0.1.0-alpha.7`
- `com.android.tools.build:gradle -> 7.0.4`
- `com.diffplug.spotless:spotless-plugin-gradle -> 6.0.4`
- `ru.vyarus:gradle-mkdocs-plugin -> 2.2.0`
- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.22`
- `gradle -> 7.3.1`

## [0.1.0-rc.22] - 2021-12-01

### Fixed

- `kotlin-library` source sets config in `GradlePlugin`

## [0.1.0-rc.21] - 2021-12-01

### Changed

- `kotlin-library` uses Java 11 if the consumer is a Gradle plugin

## [0.1.0-rc.20] - 2021-11-30

### Updated

- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.19`
- `com.javiersc.semver:semver-gradle-plugin -> 0.1.0-alpha.6`
- `com.javiersc.semver:semver-core -> 0.1.0-beta.8`
- `com.javiersc.kotlin:kotlin-stdlib -> 0.1.0-alpha.3`
- `org.eclipse.jgit:org.eclipse.jgit -> 6.0.0.202111291000-r`

## [0.1.0-rc.19] - 2021-11-29

### Changed

- `kotlin-library` uses Java 8 instead of Java 11

## [0.1.0-rc.18] - 2021-11-29

### Fixed

- `changelog` has duplicated dependencies in `Updated` with Renovate

### Updated

- `com.github.triplet.gradle:play-publisher -> 3.7.0`
- `com.javiersc.kotlin:kotlin-stdlib -> 0.1.0-alpha.2`
- `io.gitlab.arturbosch.detekt:detekt-gradle-plugin -> 1.19.0`
- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.17`

## [0.1.0-rc.17] - 2021-11-28

### Fixed

- `docs` uses incorrect Dokka output directory

### Updated

- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.16`

## [0.1.0-rc.16] - 2021-11-28

- No changes

## [0.1.0-rc.15] - 2021-11-28

### Changed

- plugin accessors are only in `all-plugins` instead of in each plugin
- `mergeChangelog` merges all versions if possible instead of only the current one

### Updated

- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.8`

## [0.1.0-rc.14] - 2021-11-25

### Fixed

- Fix configuring multiple publications at same time

## [0.1.0-rc.13] - 2021-11-25

### Added

- publish Android applications to `publish`
- publish Java Platforms to `publish`

### Changed

- `nexus` repository description
- show missing variables warning message only if `publish` task is called

### Updated

- `org.jetbrains.dokka:dokka-gradle-plugin -> 1.6.0`
- `com.gradle.enterprise:com.gradle.enterprise.gradle.plugin -> 3.7.2`

## [0.1.0-rc.12] - 2021-11-18

### Updated

- `com.javiersc.semver:semver-gradle-plugin -> 0.1.0-alpha.3`
- `com.facebook:ktfmt -> 0.30`
- `com.gradle.publish:plugin-publish-plugin -> 0.18.0`
- `org.jetbrains.kotlin:kotlin-gradle-plugin -> 1.6.0`

## [0.1.0-rc.11] - 2021-11-14

- No changes

## [0.1.0-rc.10] - 2021-11-14

### Changed

- use `semver-gradle-version` instead of `reckon` in `versioning`

## [0.1.0-rc.9] - 2021-11-13

### Added

- `testLogging.showStandardStreams = true` to `all-projects`
- `compose-resources` plugin accessor
- `semver` plugin accessor

### Changed

- `all-projects` uses fewer cores to run test to avoid freezing the computer
- `nexus` timeouts to 30 minutes
- `readme-badges-generator` uses `build-kotlin` instead of `build`

### Updated

- `com.javiersc.semantic-versioning:semantic-versioning-core -> 0.1.0-beta.6`
- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.8`
- `gradle -> 7.3`

## [0.1.0-rc.8] - 2021-11-09

### Removed

- `build-version-catalogs` plugin
- `build-version-catalogs-updater` plugin

## [0.1.0-rc.7] - 2021-11-09

### Updated

- `com.diffplug.spotless:spotless-plugin-gradle -> 6.0.0`

## [0.1.0-rc.6] - 2021-11-09

### Updated

- `gradle -> 7.3-rc-5`
- `org.jetbrains.kotlinx:binary-compatibility-validator -> 0.8.0`
- `com.javiersc.semantic-versioning:semantic-versioning-core -> 0.1.0-beta.5`

## [0.1.0-rc.5] - 2021-11-08

### Added

- `all-projects` tests result pretty output in terminal

### Updated

- `com.javiersc.semantic-versioning:semantic-versioning-core -> 0.1.0-beta.4`
- `com.gradle.publish:plugin-publish-plugin -> 0.17.0`
- `com.gradle.enterprise:com.gradle.enterprise.gradle.plugin -> 3.7.1`
- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.4`

## [0.1.0-rc.4] - 2021-11-02

- No changes

## [0.1.0-rc.3] - 2021-11-02

### Updated

- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.2`

## [0.1.0-rc.2] - 2021-10-31

### Added

- `javiersc-kotlin-library`
- `publish.onlySignificant` Gradle property to allow publishing insignificant versions

### Removed

- `javiersc-android-library`
- `javiersc-kotlin-jvm`
- `javiersc-kotlin-multiplatform`
- `javiersc-kotlin-multiplatform-no-android`

### Updated

- `com.google.code.gson:gson -> 2.8.9`
- `gradle -> 7.3-rc-3`
- `com.diffplug.spotless:spotless-plugin-gradle -> 5.17.1`
- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-rc.1`

## [0.1.0-rc.1] - 2021-10-24

### Updated

- `com.javiersc.gradle-plugins:all-plugins -> 0.1.0-beta.5`

## [0.1.0-beta.5] - 2021-10-23

### Fixed

- `PluginBundleExtension` is not found

## [0.1.0-beta.4] - 2021-10-23

### Fixed

- `initializeSonatypeStagingRepository` is not found

## [0.1.0-beta.3] - 2021-10-23

### Added

- `publish` plugin for publishing all types of projects
- `all-plugins` project

### Changed

- `buildProjectsVersionCatalog` runs before `generateCatalogAsToml` if exists, else before `build`

### Removed

- `publish-android-library`
- `publish-gradle-plugin`
- `publish-kotlin-jvm`
- `publish-kotlin-multiplatform`
- `publish-version-catalog`
- `version-catalog` in `publish-version-catalog` plugin
- `id("com.gradle.plugin-publish")` in `publish-gradle-plugin` plugin

### Updated

- `com.javiersc.gradle-plugins:versioning -> 0.1.0-beta.2`
- `com.javiersc.gradle-plugins:readme-badges -> 0.1.0-beta.2`
- `com.javiersc.gradle-plugins:publish-version-catalog -> 0.1.0-beta.2`
- `com.javiersc.gradle-plugins:publish-kotlin-jvm -> 0.1.0-beta.2`
- `com.javiersc.gradle-plugins:publish-gradle-plugin -> 0.1.0-beta.2`
- `com.javiersc.gradle-plugins:plugin-accessors -> 0.1.0-beta.2`
- `com.javiersc.gradle-plugins:nexus -> 0.1.0-beta.2`
- `com.javiersc.gradle-plugins:kotlin-multiplatform -> 0.1.0-beta.2`
- `com.javiersc.gradle-plugins:docs -> 0.1.0-beta.2`
- `com.javiersc.gradle-plugins:code-formatter -> 0.1.0-beta.2`
- `com.javiersc.gradle-plugins:code-analysis -> 0.1.0-beta.2`
- `com.javiersc.gradle-plugins:changelog -> 0.1.0-beta.2`
- `com.javiersc.gradle-plugins:all-projects -> 0.1.0-beta.2`
- `com.javiersc.gradle-plugins:android-library -> 0.1.0-beta.2`

## [0.1.0-beta.2] - 2021-10-23

### Changed

- `buildProjectsVersionCatalog` task runs before `build` task
- `buildProjectsVersionCatalog` group to `build`

### Updated

- `com.javiersc.gradle-plugins:versioning -> 0.1.0-beta.1`
- `com.javiersc.gradle-plugins:readme-badges -> 0.1.0-beta.1`
- `com.javiersc.gradle-plugins:publish-version-catalog -> 0.1.0-beta.1`
- `com.javiersc.gradle-plugins:publish-kotlin-jvm -> 0.1.0-beta.1`
- `com.javiersc.gradle-plugins:publish-gradle-plugin -> 0.1.0-beta.1`
- `com.javiersc.gradle-plugins:plugin-accessors -> 0.1.0-beta.1`
- `com.javiersc.gradle-plugins:nexus -> 0.1.0-beta.1`
- `com.javiersc.gradle-plugins:kotlin-multiplatform -> 0.1.0-beta.1`
- `com.javiersc.gradle-plugins:docs -> 0.1.0-beta.1`
- `com.javiersc.gradle-plugins:code-formatter -> 0.1.0-beta.1`
- `com.javiersc.gradle-plugins:code-analysis -> 0.1.0-beta.1`
- `com.javiersc.gradle-plugins:changelog -> 0.1.0-beta.1`
- `com.javiersc.gradle-plugins:all-projects -> 0.1.0-beta.1`
- `com.javiersc.gradle-plugins:android-library -> 0.1.0-beta.1`

## [0.1.0-beta.1] - 2021-10-22

### Changed

- `AddChangelogItem` removes duplicated dependencies with different version in `[Unreleased]`

### Updated

- `com.javiersc.gradle-plugins:versioning -> 0.1.0-alpha.72`
- `com.javiersc.gradle-plugins:readme-badges -> 0.1.0-alpha.72`
- `com.javiersc.gradle-plugins:publish-version-catalog -> 0.1.0-alpha.72`
- `com.javiersc.gradle-plugins:publish-kotlin-jvm -> 0.1.0-alpha.72`
- `com.javiersc.gradle-plugins:publish-gradle-plugin -> 0.1.0-alpha.72`
- `com.javiersc.gradle-plugins:plugin-accessors -> 0.1.0-alpha.72`
- `com.javiersc.gradle-plugins:nexus -> 0.1.0-alpha.72`
- `com.javiersc.gradle-plugins:kotlin-multiplatform -> 0.1.0-alpha.72`
- `com.javiersc.gradle-plugins:docs -> 0.1.0-alpha.72`
- `com.javiersc.gradle-plugins:code-formatter -> 0.1.0-alpha.72`
- `com.javiersc.gradle-plugins:code-analysis -> 0.1.0-alpha.72`
- `com.javiersc.gradle-plugins:changelog -> 0.1.0-alpha.72`
- `com.javiersc.gradle-plugins:all-projects -> 0.1.0-alpha.72`
- `com.javiersc.gradle-plugins:android-library -> 0.1.0-alpha.72`

## [0.1.0-alpha.72] - 2021-10-22

### Added

- `projects-version-catalog` plugin

### Updated

- `com.gradle.enterprise:com.gradle.enterprise.gradle.plugin -> 3.7`
- `com.javiersc.gradle-plugins:versioning -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:readme-badges -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:publish-kotlin-jvm -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:publish-gradle-plugin -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:plugin-accessors -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:nexus -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:kotlin-multiplatform -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:docs -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:code-formatter -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:code-analysis -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:changelog -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:all-projects -> 0.1.0-alpha.71`
- `com.javiersc.gradle-plugins:android-library -> 0.1.0-alpha.71`

## [0.1.0-alpha.71] - 2021-10-18

- No changes

## [0.1.0-alpha.70] - 2021-10-18

### Fixed

- `code-formatter` is not excluding some `resource` dirs

## [0.1.0-alpha.69] - 2021-10-18

### Fixed

- `code-formatter` is not excluding some `build` dirs

## [0.1.0-alpha.68] - 2021-10-18

### Changed

- `code-formatter` scans Kotlin files only in `kotlin` and `resource` directories
- `docs` plugin uses nested directories for `projects` in nav bar

### Updated

- `com.javiersc.gradle-plugins:versioning -> 0.1.0-alpha.67`
- `com.javiersc.gradle-plugins:readme-badges -> 0.1.0-alpha.67`
- `com.javiersc.gradle-plugins:publish-kotlin-jvm -> 0.1.0-alpha.67`
- `com.javiersc.gradle-plugins:publish-gradle-plugin -> 0.1.0-alpha.67`
- `com.javiersc.gradle-plugins:plugin-accessors -> 0.1.0-alpha.67`
- `com.javiersc.gradle-plugins:nexus -> 0.1.0-alpha.67`
- `com.javiersc.gradle-plugins:kotlin-multiplatform -> 0.1.0-alpha.67`
- `com.javiersc.gradle-plugins:docs -> 0.1.0-alpha.67`
- `com.javiersc.gradle-plugins:code-formatter -> 0.1.0-alpha.67`
- `com.javiersc.gradle-plugins:code-analysis -> 0.1.0-alpha.67`
- `com.javiersc.gradle-plugins:changelog -> 0.1.0-alpha.67`
- `com.javiersc.gradle-plugins:all-projects -> 0.1.0-alpha.67`
- `com.javiersc.gradle-plugins:android-library -> 0.1.0-alpha.67`

## [0.1.0-alpha.67] - 2021-10-15

### Updated

- `com.diffplug.spotless:spotless-plugin-gradle -> 5.17.0`
- `org.jetbrains.intellij.plugins:gradle-changelog-plugin -> 1.3.1`

## [0.1.0-alpha.66] - 2021-10-13

### Fixed

- `renovateCommitTable` doesn't work inside GitHub Actions workflows

## [0.1.0-alpha.65] - 2021-10-12

- No changes

## [0.1.0-alpha.64] - 2021-10-12

### Added

- `renovatePath` and `renovateCommitTable` to `AddChangelogItemTask`

## [0.1.0-alpha.63] - 2021-10-12

### Added

- `AddChangelogItemTask` to `changelog` plugin

## [0.1.0-alpha.62] - 2021-10-10

### Fixed

- changelog plugin `header` doesn't set the version

## [0.1.0-alpha.61] - 2021-10-10

### Fixed

- changelog plugin `header` doesn't set the version

## [0.1.0-alpha.60] - 2021-10-10

- No changes

## [0.1.0-alpha.59] - 2021-09-27

- No changes

## [0.1.0-alpha.58] - 2021-09-27

- No changes

## [0.1.0-alpha.57] - 2021-09-27

- No changes

## [0.1.0-alpha.56] - 2021-09-25

### Added

- all plugins to Gradle Plugin Portal

## [0.1.0-alpha.55] - 2021-09-25

### Fixed

- plugin publications were using incorrect `website` and `vcsUrl`

## [0.1.0-alpha.54] - 2021-09-12

### Added

- `kotlin-multiplatform-no-android` without applying Android plugin

### Removed

- `all-plugins`

## [0.1.0-alpha.53] - 2021-09-12

### Added

- `kotlin-multiplatform` applies AGP and has a default Android config

## [0.1.0-alpha.52] - 2021-09-11

### Fixed

- `code-formatting` search for `kt` files in `build` folders

## [0.1.0-alpha.51] - 2021-09-11

### Fixed

- `code-formatter` searching Kotlin files only on `src`

## [0.1.0-alpha.50] - 2021-09-11

### Added

- `kotlin-jvm`
- `kotlin-multiplatform` accessors for all targets

### Changed

- `android-library` improvements:
    - `src` folder is no longer necessary
    - new project structure:
        - `main/AndroidManifest.xml`
        - `main/assets`
        - `main/java`
        - `main/kotlin`
        - `main/res`
        - `main/resources`
- `kotlin-multiplatform` improvements:
    - `src` fodler is no longer necessary
    - new project structure:
        - `targetMain/kotlin`
        - `targetMain/resources`

### Removed

- `isSnapshot` and use `reckon.stage` to check if it is a snapshot

## [0.1.0-alpha.49] - 2021-08-21

- No changes

## [0.1.0-alpha.48] - 2021-08-21

### Fixed

- `build-version-catalogs` build files path

## [0.1.0-alpha.47] - 2021-08-20

### Removed

- unnecessary error CLI messages when using `publish-*` plugins

## [0.1.0-alpha.46] - 2021-08-14

### Changed

- `publish-*` plugins can use Gradle properties, environment variables or both mixed.
- `publish-*` plugins will use `useInMemoryPgpKeys` as default method which fallbacks to `useGpgCmd`
- `GPG_KEY_NAME` renamed to `SIGNING_KEY_NAME`
- `GPG_KEY_PASSPHRASE` renamed to `SIGNING_KEY_PASSPHRASE`
- `GPG_PRIVATE_KEY` renamed to `SIGNING_KEY`

## [0.1.0-alpha.45] - 2021-08-14

### Fixed

- `build-version-catalogs` wrong path in Unix based systems

## [0.1.0-alpha.44] - 2021-08-13

### Fixed

- `build-version-catalogs` wrong path in Unix based systems

## [0.1.0-alpha.43] - 2021-08-13

### Fixed

- `build-version-catalogs` generates the version in wrong path

## [0.1.0-alpha.42] - 2021-08-13

### Added

- `build-version-catalogs` supports empty version
- `build-version-catalogs` supports not setting `bundles`

### Changed

- `build-version-catalogs` generates the catalogs in `build/catalogs` instead of `gradle/catalogs`

## [0.1.0-alpha.41] - 2021-08-13

- No changes

## [0.1.0-alpha.40] - 2021-06-30

### Fixed

- `plugin-accessors` can't be used if a `javiersc` plugin is present

## [0.1.0-alpha.39] - 2021-06-28

### Added

- `massive-catalogs-updater` saves version in the root project `build/versions/massive-catalogs.txt`

## [0.1.0-alpha.38] - 2021-06-28

### Changed

- `updateGradlew` to `updateGradleWrapper`
- `massive-catalogs-updater` fetches directly from the maven repo

### Fixed

- `gradle-wrapper-updater-task`

## [0.1.0-alpha.37] - 2021-06-27

- No changes

## [0.1.0-alpha.36] - 2021-06-27

### Fixed

- `publishing` for `plugin-accessors`
- registering tasks without `doLast`

## [0.1.0-alpha.35] - 2021-06-27

### Added

- `plugin-accessors`
- `massive-catalogs-updater` plugin

## [0.1.0-alpha.34] - 2021-06-24

### Added

- `update-gradle-wrapper` plugin

## [0.1.0-alpha.33] - 2021-06-23

### Added

- `group` to all registered Gradle tasks

### Fixed

- Crash when `Changelog.md` has no initial version

## [0.1.0-alpha.32] - 2021-06-22

### Added

- `android-library`
- `kotlin-multiplatform` plugin flatten folders structure

### Changed

- `pomName` to `pom.name`
- `pomDescription` to `pom.description`
- `pomUrl` to `pom.url`
- `pomLicenseName` to `pom.license.name`
- `pomLicenseUrl` to `pom.license.url`
- `pomDeveloperId` to `pom.developer.id`
- `pomDeveloperName` to `pom.developer.name`
- `pomDeveloperEmail` to `pom.developer.email`
- `pom.smc.url` to `pom.smc.url`
- `pom.smc.connection` to `pom.smc.connection`
- `pomSmcDeveloperConnection` to `pom.smc.developerConnection`

### Fixed

- `readme-badges-generator` not printing the library
- `docs` not printing there are no markdown files

## [0.1.0-alpha.31] - 2021-05-07

### Added

- `code-formatter` uses ktfmt version from Massive Catalogs

## [0.1.0-alpha.30] - 2021-05-06

### Changed

- `all-projects` applies `LifecycleBasePlugin` to generate the `clean` task

### Removed

- `all-projects` clean task

## [0.1.0-alpha.29] - 2021-05-06

### Fixed

- `all-project` clean task

## [0.1.0-alpha.28] - 2021-05-05

### Added

- Add to `Test` type tasks a few improvements by default

### Fixed

- `docs` looking for an incorrect path in all `**.md` projects files
- Fix `clean` task being registered even with it already exists

## [0.1.0-alpha.27] - 2021-05-05

### Fixed

- `docs` plugin replace wrong asset path in `README.md` file
- `docs` looking for an incorrect `index.md` file path

## [0.1.0-alpha.26] - 2021-05-05

### Changed

- mkdocs template, favicon and logo properties, in `docs`

## [0.1.0-alpha.25] - 2021-05-05

### Added

- `all-projects`
    - Default config like set up `group` for all projects
    - Delete root `build` directory when `clean` task is called

### Changed

- Rename `libGroup` to `allProjects.group`
- Rename `libName` to `allProjects.name`

## [0.1.0-alpha.24] - 2021-05-04

### Changed

- `readme-badges-generator` renamed to `readme-badges`
- `mainSubProject` property renamed to `readmeBadges.mainProject`
- `shouldGenerateVersionBadgePerProject` renamed to `readmeBadges.allProjects`

### Fixed

- `docs` forces using markdown files in all modules even if they shouldn't have docs

## [0.1.0-alpha.23] - 2021-05-03

### Fixed

- Fix applying incorrect id in `code-formatter`

## [0.1.0-alpha.22] - 2021-05-03

- No changes

## [0.1.0-alpha.21] - 2021-05-02

### Added

- `docs` plugin autogenerate `.docs` and children dirs if they don't exist
- `docs` can generate a complete website via mkdocs
- `docs` autogenerate nav bar by adding Changelog, API docs and Projects
- Autogenerate detekt IDEA plugin config in `.idea` directory
- Autogenerate ktfmt IDEA plugin config in `.idea` directory

### Changed

- `code-formatter` is applied to all Kotlin projects by adding it in root `build.gradle.kts`
- `readme-badges-generator` rename `generateReadmeBadges` task to `buildReadmeBadges`
- Changelog registers `mergeChangelog` task instead of merging automatically with `patchChangelog`
- Dokka is automatically applied to all projects that are applying Kotlin Gradle plugin

### Removed

- `jcenter()`

### Fixed

- module docs generated hasn't `MODULE.md` info
- All `publish-*` plugins have using Dokka incorrectly

## [0.1.0-alpha.20] - 2021-04-27

### Added

- `changelog` can merge non-final versions into one final version
- `readme-badges-generator` prints Kotlin version

## [0.1.0-alpha.19] - 2021-04-27

- No changes

## [0.1.0-alpha.18] - 2021-04-26

### Added

- Add `all-plugins` module which exposes all plugins

### Removed

- ktfmt uses the default Spotless ktfmt version instead of indicating the last one
- Detekt version is not needed

### Fixed

- Kotlin version is found by checking all the projects instead of from Version Catalog

## [0.1.0-alpha.17] - 2021-04-26

### Updated

- Gradle Wrapper to 7.0

## [0.1.0-alpha.16] - 2021-04-24

### Fixed

- `publish-android-library` doesn't sign the release publication

## [0.1.0-alpha.15] - 2021-04-22

### Fixed

- `publish-android-library` doesn't found any component
- `detekt` warnings

## [0.1.0-alpha.14] - 2021-03-22

### Fixed

- `changelog` was throwing an exception

## [0.1.0-alpha.13] - 2021-03-21

### Fixed

- `changelog` was throwing an exception when it was being used by first time

## [0.1.0-alpha.12] - 2021-03-21

### Fixed

- `changelog` output format was incorrect

## [0.1.0-alpha.11] - 2021-03-21

### Fixed

- `publish-version-catalog` wasn't applying `version-catalog` plugin

## [0.1.0-alpha.10] - 2021-03-21

### Changed

- `readme-badges-generator` registers `generateReadmeBadges` instead of generate badges when syncing

### Fixed

- `changelog` output format was incorrect

## [0.1.0-alpha.9] - 2021-03-21

### Fixed

- `publish-*` plugins were not working

## [0.1.0-alpha.8] - 2021-03-21

### Changed

- Separate `publish` plugin into:
    - `publish-android-library`
    - `publish-kotlin-jvm`
    - `publish-kotlin-multiplatform`
    - `publish-version-catalog`
- Show git diff when `checkIsSignificant` is invoked in all `publish` plugins

## [0.1.0-alpha.7] - 2021-03-20

- No changes

## [0.1.0-alpha.6] - 2021-03-20

### Added

- plugins
    - publish-gradle-plugin

## [0.1.0-alpha.5] - 2021-03-20

### Added

- support to `com.android.library` and `version-catalog` plugins in `publish`

### Changed

- Move `checkIsSignificant` Gradle task from `versioning` to `publish`

### Fixed

- `changelog` plugin was adding to the end of the file two new lines

## [0.1.0-alpha.4] - 2021-03-20

- No changes

## [0.1.0-alpha.3] - 2021-03-19

### Added

- expose `getDefaultLanguageSettings`

## [0.1.0-alpha.2] - 2021-03-18

- No changes

## [0.1.0-alpha.1] - 2021-03-18

### Added

- plugins
    - changelog
    - code-analysis
    - code-formatter
    - dependency-updates
    - docs
    - kotlin-multiplatform
    - nexus
    - publish
    - readme-badges-generator
    - versioning
