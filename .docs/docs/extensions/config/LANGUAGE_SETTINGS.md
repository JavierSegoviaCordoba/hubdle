# Explicit API

Enable or disable language settings error or warnings.

## Overview

- languageSettings
    - `isEnabled: Property<Boolean>` // false
    - `enabled(value: Boolean = true)`
    - `experimentalContracts()`
    - `experimentalCoroutinesApi()`
    - `experimentalSerializationApi()`
    - `experimentalStdlibApi()`
    - `experimentalTime()`
    - `flowPreview()`
    - `ktorInternalAPI()`
    - `requiresOptIn()`
    - `optIn(annotationName: String)`
    - `optIn(vararg annotationNames: String)`
    - `languageSettings(action: Action<LanguageSettingsBuilder>)`

## Configuration

### Basic config

```kotlin
hubdle {
    config {
       languageSettings {
           experimentalStdlib()
       } 
    }
}
```

### Advanced config

```kotlin
hubdle {
    config {
       languageSettings {
           experimentalCoroutinesApi()
           experimentalStdlibApi()
           experimentalTime()
           flowPreview()
           requiresOptIn()
           optIn("org.example.SomeAnnotation")
       }
    }
}
```
