# Binary compatibility validator

Check if there are any binary compatibility issues.

## Overview

- binaryCompatibilityValidator
    - `isEnabled: Property<Boolean>` // false
    - `enabled(value: Boolean = true)`
    - `apiValidation(action: Action<ApiValidationExtension>)`

## Configuration

It must be applied only in the root project.

```kotlin
hubdle {
    config {
       binaryCompatibilityValidator() 
    }
}
```

## Tasks

```shell
./gradlew checkApi
```

```shell
./gradlew dumpApi
```
