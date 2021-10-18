# Module projects-version-catalog

Autogenerate a Version Catalog with all projects

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:projects-version-catalog:$version")
}
```

### Apply

Add it to the root `build.gradle.kts`

```kotlin
plugins {
    id("com.javiersc.gradle.plugins.projects.version.catalog")
}
```

### Usage

```kotlin
// all properties are not required
projectsVersionCatalog {
    librariesPrefix.set("jsc") // default `""`
    removeVersionAliasPrefix.set("com") // default `""`
    projects.set(listOf(project("..."))) // default all projects that apply `maven-publish`
    tomlPath.set("my-tomlfile.toml") // default `gradle/projects.versions.toml`
}
```

The plugin will create a TOML file with all projects in it. If the TOML file already exists and it
has `[bundles]`, they will be added too.
