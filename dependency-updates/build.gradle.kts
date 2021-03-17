plugins {
    `kotlin-dsl`
    publish
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
    implementation(files(pluginLibs.javaClass.protectionDomain.codeSource.location))

    api(projects.accessors)

    api(pluginLibs.github.benManes.gradleVersionsPlugin)
}
