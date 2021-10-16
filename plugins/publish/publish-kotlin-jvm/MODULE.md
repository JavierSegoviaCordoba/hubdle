# Module publish-kotlin-jvm

A custom plugin for publishing Kotlin JVM projects which uses `maven-publish` under the hood.

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:publish-kotlin-jvm:$version")
}
```

### Apply

Add it to all Kotlin JVM modules in the project that should be published to MavenCentral.

```kotlin
plugins {
    `javiersc-publish-kotlin-jvm`
}
```

### Usage

Just apply it and add the next Gradle properties (replace the values of each property with the
correct ones):

```properties
####################################################################################################
###                                           POM                                                ###
####################################################################################################
pom.name=Gradle Plugins
pom.description=Gradle Plugins utilities
pom.url=https://github.com/JavierSegoviaCordoba/gradle-plugins
pom.license.name=The Apache License, Version 2.0
pom.license.url="https://www.apache.org/licenses/LICENSE-2.0.txt"
pom.developer.id=JavierSegoviaCordoba
pom.developer.name=Javier Segovia Cordoba
pom.developer.email=javier@segoviacordoba.com
pom.smc.url=https://github.com/JavierSegoviaCordoba/gradle-plugins
pom.smc.connection=scm:git:git@github.com:JavierSegoviaCordoba/gradle-plugins.git
pom.smc.developerConnection=scm:git:git@github.com:JavierSegoviaCordoba/gradle-plugins.git
```
