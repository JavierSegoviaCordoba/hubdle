plugins {
    `kotlin-dsl`
    `javiersc-publish-gradle-plugin`
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
    api(projects.projects.pluginAccessors)
    api(projects.projects.core)

    api(pluginLibs.android.toolsBuild.gradle)
}
