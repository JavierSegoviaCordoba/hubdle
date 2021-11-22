# Module publish

A Gradle plugin for publishing any type of project:

- Android applications:
    - `id("com.android.application")`
- Android libraries:
    - `id("com.android.library")`
- Gradle plugins:
    - `java-gradle-plugin`
    - `kotlin-dsl`
- Java Platforms:
    - `java-platform`
- Kotlin JVM libraries:
    - `kotlin("jvm")`
    - `id("org.jetbrains.kotlin.jvm")`
- Kotlin Multiplatform libraries:
    - `kotlin("multiplatform")`
    - `id("org.jetbrains.kotlin.multiplatform")`
- Version Catalogs:
    - `version-catalog`

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:publish:$version")
}
```

### Apply

Add it to all projects that need to be published.

```kotlin
plugins {
    kotlin("jvm") // or whatever plugin
    `javiersc-publish`
}
```

### Usage

Add the next Gradle properties (replace the values of each property with the correct one):

```properties
####################################################################################################
###                                           POM                                                ###
####################################################################################################
pom.name=Some project
pom.description=Some project description
pom.url=https://github.com/SomeDeveloper/some-project
pom.license.name=The Apache License, Version 2.0
pom.license.url=https://www.apache.org/licenses/LICENSE-2.0.txt
pom.developer.id=SomeDeveloper
pom.developer.name=Some Developer
pom.developer.email=some.developer@example.com
pom.smc.url=https://github.com/SomeDeveloper/some-project
pom.smc.connection=scm:git:git@github.com:SomeDeveloper/some-project.git
pom.smc.developerConnection=scm:git:git@github.com:SomeDeveloper/some-project.git
```

It is possible to publish insignificant versions by setting the Gradle
property `publish.onlySignificant` to `false:

- `gradle.properties` file:

```properties
publish.onlySignificant=false
```

- Terminal:

```shell
./gradlew publishToSonatype "-Ppublish.onlySignificant=false"
```

#### Additional config for specific projects

##### Gradle plugin

```kotlin
gradlePlugin {
    plugins {
        named("SomeName") {
            id = "com.example.some.id"
            displayName = "Some Gradle plugin name"
            description = "Some Gradle plugin description"
        }
    }
}
```

##### Version Catalog

```kotlin
catalog {
    versionCatalog {
        from(files("path/to/the/toml/file"))
    }
}
```
