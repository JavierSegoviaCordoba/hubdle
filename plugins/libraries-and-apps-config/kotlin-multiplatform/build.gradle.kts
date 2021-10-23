plugins {
    `kotlin-dsl`
    `javiersc-publish-gradle-plugin`
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
    api(projects.shared.androidCore)
    api(projects.shared.core)
    api(projects.shared.kotlinMultiplatformCore)
    api(projects.shared.pluginAccessors)

    compileOnly(pluginLibs.android.toolsBuild.gradle)
    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePluginX)
}
