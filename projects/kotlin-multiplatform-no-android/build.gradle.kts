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
        named("com.javiersc.gradle.plugins.kotlin.multiplatform.no.android") {
            id = "com.javiersc.gradle.plugins.kotlin.multiplatform.no.android"
            displayName = "Kotlin Multiplatform No Android"
            description =
                "A custom plugin for `kotlin(\"multiplatform\")` plugin with a basic setup " +
                    "without Android"
        }
    }
}

dependencies {
    api(projects.projects.core)
    api(projects.projects.kotlinMultiplatformCore)
    api(projects.projects.pluginAccessors)

    implementation(pluginLibs.jetbrains.kotlin.kotlinGradlePluginX)
}
