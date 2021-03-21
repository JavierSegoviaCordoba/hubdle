# Changelog

## [Unreleased]

### Added

### Changed

### Deprecated

### Removed

### Fixed

### Updated


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
