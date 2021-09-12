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
    api(projects.projects.pluginAccessors)
    api(projects.projects.core)

    implementation(pluginLibs.android.toolsBuild.gradle)
    implementation(pluginLibs.jetbrains.kotlin.kotlinGradlePluginX)
}
