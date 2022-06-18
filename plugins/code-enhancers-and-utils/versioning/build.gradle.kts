plugins {
    `javiersc-versioning`
    `java-gradle-plugin`
    `kotlin-jvm`
    `javiersc-kotlin-config`
    `javiersc-publish`
}

pluginBundle {
    tags =
        listOf(
            "versioning",
            "semver",
            "semantic versioning",
            "semantic version",
            "git",
        )
}

gradlePlugin {
    plugins {
        create("com.javiersc.gradle.plugins.versioning") {
            id = "com.javiersc.gradle.plugins.versioning"
            displayName = "Versioning"
            description = "A custom plugin for Semver Gradle Plugin for git versioning"
            implementationClass = "com.javiersc.gradle.plugins.versioning.VersioningPlugin"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)

    api(pluginLibs.javiersc.semver.semverGradlePlugin)
}
