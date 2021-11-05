plugins {
    `kotlin-dsl`
    `javiersc-publish`
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
    api(projects.shared.pluginAccessors)

    implementation(pluginLibs.adarshr.gradleTestLoggerPlugin)
}
