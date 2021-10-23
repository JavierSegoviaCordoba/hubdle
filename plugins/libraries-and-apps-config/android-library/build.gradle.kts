plugins {
    `kotlin-dsl`
    `javiersc-publish-gradle-plugin`
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
    api(projects.shared.androidCore)
    api(projects.shared.core)
    api(projects.shared.pluginAccessors)

    compileOnly(pluginLibs.android.toolsBuild.gradle)
    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePluginX)
}
