plugins {
    `kotlin-dsl`
    publish
    `plugin-publish`
    `accessors-generator`
}

pluginBundle {
    tags =
        listOf(
            "gradle",
            "grapper",
            "updater",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.gradle.wrapper.updater") {
            id = "com.javiersc.gradle.plugins.gradle.wrapper.updater"
            displayName = "Gradle Wrapper Updater"
            description = "A plugin for updating the Gradle Wrapper"
        }
    }
}

dependencies {
    api(projects.accessors)
    api(projects.core)

    implementation(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
}
