plugins {
    `kotlin-dsl`
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
        named("com.javiersc.gradle.plugins.versioning") {
            id = "com.javiersc.gradle.plugins.versioning"
            displayName = "Versioning"
            description = "A custom plugin for Semver Gradle Plugin for git versioning"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)

    api(pluginLibs.javiersc.semver.semverGradlePlugin)
}
