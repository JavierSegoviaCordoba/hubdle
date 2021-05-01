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
pomName=Gradle Plugins
pomDescription=Gradle Plugins utilities
pomUrl=https://github.com/JavierSegoviaCordoba/gradle-plugins
pomLicenseName=The Apache License, Version 2.0
pomLicenseUrl="https://www.apache.org/licenses/LICENSE-2.0.txt"
pomDeveloperId=JavierSegoviaCordoba
pomDeveloperName=Javier Segovia Cordoba
pomDeveloperEmail=javier@segoviacordoba.com
pomSmcUrl=https://github.com/JavierSegoviaCordoba/gradle-plugins
pomSmcConnection=scm:git:git@github.com:JavierSegoviaCordoba/gradle-plugins.git
pomSmcDeveloperConnection=scm:git:git@github.com:JavierSegoviaCordoba/gradle-plugins.git
```
