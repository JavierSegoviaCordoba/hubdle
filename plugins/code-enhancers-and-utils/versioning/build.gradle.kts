plugins {
    `kotlin-dsl`
    `javiersc-publish-gradle-plugin`
}

pluginBundle {
    tags =
        listOf(
            "versioning",
            "reckon",
            "git",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.versioning") {
            id = "com.javiersc.gradle.plugins.versioning"
            displayName = "Versioning"
            description = "A custom plugin for Reckon Plugin and its git versioning"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)
    api(projects.shared.core)

    api(pluginLibs.ajoberstar.reckon.reckonGradle)
}
