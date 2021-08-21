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
        named("com.javiersc.gradle.plugins.dependency.updates") {
            id = "com.javiersc.gradle.plugins.dependency.updates"
            displayName = "Dependency Updates"
            description = "A custom plugin for Ben Manes Versions plugin with basic setup"
        }
    }
}

dependencies {
    api(projects.pluginAccessors)

    api(pluginLibs.github.benManes.gradleVersionsPluginX)
}
