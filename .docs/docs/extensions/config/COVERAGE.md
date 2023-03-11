# Coverage

Get code coverage with Kover.

## Overview

- coverage
    - `isEnabled: Property<Boolean>` // false
    - `enabled(value: Boolean = true)`
    - `engine: Property<CoverageEngineVariant>` // DefaultJacocoEngine
    - `kover(action: Action<KoverProjectExtension>)`

## Configuration

It must be applied only in the root project.

```kotlin
hubdle {
    config {
       coverage() 
    }
}
```
