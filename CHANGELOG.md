# Changelog

## [Unreleased]

### Added

### Changed

### Deprecated

### Removed

### Fixed

### Updated


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
- expose `defaultLanguageSettings`

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
