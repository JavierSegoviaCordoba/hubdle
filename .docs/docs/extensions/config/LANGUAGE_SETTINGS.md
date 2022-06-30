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
           experimentalCoroutinesApi(enabled = false)
           experimentalStdlibApi(enabled = true)
           experimentalTime(enabled = true)
           flowPreview(enabled = false)
           requiresOptIn(enabled = true)
           
           rawConfig {
               languageSettings {
                   optIn("...")
               }
           }
       }
    }
}
```
