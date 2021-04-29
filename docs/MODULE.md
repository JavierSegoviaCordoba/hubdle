# Module docs

A custom plugin for Dokka Plugin with basic setup

### Download from Gradle Plugin Portal

```kotlin
plugins {
    id("com.javiersc.gradle.plugins.docs") version "$version"
}
```

### FAQ

- Avoid certain modules can be published

```kotlin
dokkaHtmlMultiModule {
    removeChildTasks(
        listOf(
            project(":subprojectA"),
            project(":subprojectB")
        )
    )
}
```
