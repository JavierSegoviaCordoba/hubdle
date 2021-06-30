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
            "android",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.android.library") {
            id = "com.javiersc.gradle.plugins.android.library"
            displayName = "Android library"
            description =
                """A custom plugin for `id("com.android.library")` plugin with a basic setup"""
        }
    }
}

dependencies {
    api(projects.pluginAccessors)
    api(projects.core)

    api(pluginLibs.android.toolsBuild.gradle)
}
