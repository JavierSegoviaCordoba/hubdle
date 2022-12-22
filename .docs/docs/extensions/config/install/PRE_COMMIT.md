# PRE-COMMIT installs

Any pre-commit can be installed easily by running:

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
                allTests.set(true)
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
./gradlew installAllTestsPreCommit
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
