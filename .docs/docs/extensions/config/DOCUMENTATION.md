# Documentation

Get a complete documentation site built with MkDocs, Dokka, and more.

## Overview

- documentation
    - `isEnabled: Property<Boolean>` // false
    - `enabled(value: Boolean = true)`
    - [changelog](changelog/CHANGELOG_EXTENSION_OVERVIEW.md)
    - [readme](readme/README_EXTENSION_OVERVIEW.md)
    - [site](site/SITE_EXTENSION_OVERVIEW.md)

## Changelog

Manage the changelog easily

### Overview

- changelog
  - `isEnabled: Property<Boolean>` // false
  - `enabled(value: Boolean = true)`
  - `changelog(action: Action<ChangelogPluginExtension>)`

### Configuration

```kotlin
hubdle {
    config {
        documentation {
            changelog()
        }
    }
}
```

### Patch the changelog

```shell
./gradlew patchChangelog
```

### Add an item to the changelog

```shell
./gradlew addChangelogItem --added "some new addition"
```

```shell
./gradlew addChangelogItem --changed "some change"
```

```shell
./gradlew addChangelogItem --removed "something because it is no longer necessary"
```

### Format the changelog

```shell
./gradlew applyFormatChangelog
```

### Merge the changelog

Merge all non-final related versions into the final one

```shell
./gradlew mergeChangelog
```
## Readme

Sync README with the current state of the project by running

```shell
./gradlew writeReadmeBadges
```

## Overview

- readme
  - `isEnabled: Property<Boolean>` // false
  - `enabled(value: Boolean = true)`
  - badges
    - `isEnabled: Property<Boolean>` // false
    - `enabled(value: Boolean = true)`
    - `kotlin: Property<Boolean>` // true
    - `mavenCentral: Property<Boolean>` // true
    - `snapshots: Property<Boolean>` // true
    - `build: Property<Boolean>` // true
    - `coverage: Property<Boolean>` // true
    - `quality: Property<Boolean>` // true
    - `techDebt: Property<Boolean>` // true

### Configuration

#### Basic config

```kotlin
hubdle {
    config {
        documentation {
            readme {
                badges()
            }
        }
    }
}
```

#### Advanced config

```kotlin
hubdle {
    config {
        documentation {
            readme {
                badges {
                    isEnabled = true
                    kotlin = true
                    mavenCentral = true
                    snapshots = true
                    build = true
                    coverage = true
                    quality = true
                    techDebt = true   
                }
            }
        }
    }
}
```

## Site

Build a site easily by running

```shell
./gradlew buildSite
```

### Overview

- site
  - `isEnabled: Property<Boolean>` // false
  - `enabled(value: Boolean = true)`
  - reports
    - `isEnabled: Property<Boolean>` // true
    - `enabled(value: Boolean = true)`
    - `codeAnalysis: Property<Boolean>` // true
    - `codeCoverage: Property<Boolean>` // true
    - `codeQuality: Property<Boolean>` // true
  - `mkdocs(action: Action<MkdocsExtension>)`

### Configuration

#### Basic config

```kotlin
hubdle {
    config {
        documentation {
            site()
        }
    }
}
```

#### Advanced config

```kotlin
hubdle {
    config {
        documentation {
            site {
                isEnabled = true
                reports {
                    codeAnalysis = true
                    codeCoverage = true
                    codeQuality = true
                }
            }
        }
    }
}
```
