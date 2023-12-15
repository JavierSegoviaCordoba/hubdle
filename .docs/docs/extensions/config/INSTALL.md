# Install

Any pre-commit can be installed easily by running:

## Overview

- install
    - `isEnabled: Property<Boolean>` // false
    - `enabled(value: Boolean = true)`
    - preCommits
        - `isEnabled: Property<Boolean>` // false
        - `enabled(value: Boolean = true)`
        - `tests: Property<Boolean>` // false
        - `applyFormat: Property<Boolean>` // false
        - `assemble: Property<Boolean>` // false
        - `checkAnalysis: Property<Boolean>` // false
        - `checkApi: Property<Boolean>` // false
        - `checkFormat: Property<Boolean>` // false
        - `dumpApi: Property<Boolean>` // false

## Configuration

### Basic config

```kotlin
hubdle {
    config {
        install {
            preCommits()
        }
    }
}
```

### Advanced config

```kotlin
hubdle {
    config {
        install {
            preCommits {
                isEnabled.set(true)
                tests.set(true)
                applyFormat.set(true)
                assemble.set(true)
                checkAnalysis.set(true)
                checkApi.set(true)
                checkFormat.set(true)
                dumpApi.set(true)
            }
        }
    }
}
```

## Tasks

```shell
./gradlew installTestsPreCommit
```

```shell
./gradlew installApplyFormatPreCommit
```

```shell
./gradlew installAssemblePreCommit
```

```shell
./gradlew installCheckAnalysisPreCommit
```

```shell
./gradlew installCheckApiPreCommit
```

```shell
./gradlew installCheckFormatPreCommit
```

```shell
./gradlew installDumpApiPreCommit
```
