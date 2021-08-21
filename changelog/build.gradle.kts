plugins {
    `kotlin-dsl`
    `javiersc-publish-gradle-plugin`
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
    api(projects.pluginAccessors)

    api(pluginLibs.jetbrains.intellijPlugins.gradleChangelogPluginX)
}
