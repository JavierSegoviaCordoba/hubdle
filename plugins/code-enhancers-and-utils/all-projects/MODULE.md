# Module all-projects

A custom plugin which a default config for all projects

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies { 
    implementation("com.javiersc.gradle-plugins:all-projects:$version") 
}
```

### Apply

In the root `build.gradle.kts`

```kotlin
plugins {
    `javiersc-all-projects`
}
```

### Usage

Define the next properties in `gradle.properties`

```properties
allProjects.group=com.javiersc
allProjects.name=gradle-plugins
```

The group for all projects will be

`group = "com.javiersc.gradle-plugins`

### Features

- CLI pretty printing test results 
- Add `allTests` task to all projects
- Create a test report in `root-project/build/reports/allTests` merging all projects reports.
