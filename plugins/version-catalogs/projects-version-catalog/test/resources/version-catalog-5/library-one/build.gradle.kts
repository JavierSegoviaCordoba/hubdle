plugins {
    `maven-publish`
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        create("SomeGradlePlugin") {
            id = "com.example.some.gradle.plugin"
            implementationClass = "com.example.some.gradle.plugin.ProjectsVersionCatalogPlugin"
            displayName = "Some Gradle Plugin name"
            description = "Some Gradle Plugin description"
        }
    }
}
