plugins {
    `kotlin-dsl`
    `javiersc-publish-gradle-plugin`
    `accessors-generator`
}

pluginBundle {
    tags =
        listOf(
            "kotlin",
            "multiplatform",
            "android",
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
    api(projects.projects.androidCore)
    api(projects.projects.core)
    api(projects.projects.kotlinMultiplatformCore)
    api(projects.projects.pluginAccessors)

    compileOnly(pluginLibs.android.toolsBuild.gradle)
    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePluginX)
}
