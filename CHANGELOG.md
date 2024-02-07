# Changelog

## [Unreleased]

### Added

- `withPlugin` function to `hubdle`
- `withPlugins` function to `hubdle`

### Changed

### Deprecated

### Removed

### Fixed

### Updated

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

[Unreleased]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.6.2...HEAD

[0.6.2]: https://github.com/JavierSegoviaCordoba/hubdle/compare/p0.6.1...p0.6.2

[0.6.1]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.6.0...0.6.1

[0.6.0]: https://github.com/JavierSegoviaCordoba/hubdle/compare/0.5.0-beta.16...0.6.0
