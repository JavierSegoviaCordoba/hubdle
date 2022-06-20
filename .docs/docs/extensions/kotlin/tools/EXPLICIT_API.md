# Explicit API

Enable or disable explicit API.

## Configuration

### Basic config

```kotlin
hubdle {
    kotlin {
       explicitApi() 
    }
}
```

### Advanced config

```kotlin
hubdle {
    kotlin {
       explicitApi(ExplicitApiMode.Strict) 
    }
}
```
