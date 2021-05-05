plugins {
    `kotlin-dsl`
    publish
    `plugin-publish`
    `accessors-generator`
}

pluginBundle {
    tags =
        listOf(
            "default config",
            "allprojects",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.all.projects") {
            id = "com.javiersc.gradle.plugins.all.projects"
            displayName = "All Projects"
            description = "A custom plugin which a default config for all projects"
        }
    }
}

dependencies {
    api(projects.accessors)
    api(projects.core)
}
