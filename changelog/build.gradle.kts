plugins {
    `kotlin-dsl`
    publish
    `plugin-publish`
    `accessors-generator`
}

pluginBundle {
    tags =
        listOf(
            "docs",
            "dokka",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.changelog") {
            id = "com.javiersc.gradle.plugins.changelog"
            displayName = "Changelog"
            description = "A custom plugin for Changelog plugin with basic setup"
        }
    }
}

dependencies {
    api(projects.accessors)

    api(pluginLibs.jetbrains.intellijPlugins.gradleChangelogPlugin)
}
