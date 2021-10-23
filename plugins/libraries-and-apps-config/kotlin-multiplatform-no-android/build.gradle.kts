plugins {
    `kotlin-dsl`
    `javiersc-publish`
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
    api(projects.shared.core)
    api(projects.shared.kotlinMultiplatformCore)
    api(projects.shared.pluginAccessors)

    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
}
