plugins {
    `kotlin-dsl`
    publish
    `plugin-publish`
    `accessors-generator`
}

pluginBundle {
    tags =
        listOf(
            "kotlin",
            "multiplatform",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.kotlin.multiplatform") {
            id = "com.javiersc.gradle.plugins.kotlin.multiplatform"
            displayName = "Kotlin Multiplatform"
            description =
                "A custom plugin for `kotlin(\"multiplatform\")` plugin with a basic setup"
        }
    }
}

dependencies {
    api(projects.accessors)
    api(projects.core)

    api(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
}
