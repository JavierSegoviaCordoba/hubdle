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
                isEnabled = true
                allTests = true
                applyFormat = true
                assemble = true
                checkAnalysis = true
                checkApi = true
                checkFormat = true
                dumpApi = true
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
