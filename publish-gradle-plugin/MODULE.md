# Module publish-gradle-plugin

A custom plugin for publishing Gradle plugins to MavenCentral and Gradle Plugin Portal which uses 
`maven-publish` and `id("com.gradle.plugin-publish")` under the hood.

### Download from MavenCentral

```kotlin
// buildSrc/build.gradle.kts

dependencies {
    implementation("com.javiersc.gradle-plugins:publish-gradle-plugin:$version")
}
```

### Apply

Add it to all Gradle plugins modules in the project that should be published to MavenCentral or
Gradle Plugin Portal

```kotlin
plugins {
    `javiersc-publish-gradle-plugin`
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
