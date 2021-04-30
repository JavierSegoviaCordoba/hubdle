# Changelog

## [Unreleased]

### Added
- `javiersc-docs` can generate a complete website via mkdocs
- Autogenerate detekt IDEA plugin config in `.idea` directory
- Autogenerate ktfmt IDEA plugin config in `.idea` directory

### Changed
- Changelog registers `mergeChangelog` task instead of merging automatically with `patchChangelog`
- Dokka is automatically applied to all projects that are applying Kotlin Gradle plugin

### Deprecated

### Removed

### Fixed
- module docs generated hasn't `MODULE.md` info

### Updated


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
