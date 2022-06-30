# Binary compatibility validator

Check if there are any binary compatibility issues.

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
