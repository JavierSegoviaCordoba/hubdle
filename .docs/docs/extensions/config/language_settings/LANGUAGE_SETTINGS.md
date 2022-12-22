# Explicit API

Enable or disable language settings error or warnings.

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
