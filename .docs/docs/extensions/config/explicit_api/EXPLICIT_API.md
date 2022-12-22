# Explicit API

Enable or disable explicit API.

## Configuration

### Basic config

```kotlin
hubdle {
    config {
       explicitApi() 
    }
}
```

### Advanced config

```kotlin
hubdle {
    config {
       explicitApi(ExplicitApiMode.Strict) 
    }
}
```
