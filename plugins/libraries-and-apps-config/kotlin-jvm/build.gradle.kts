plugins {
    `kotlin-dsl`
    `javiersc-publish`
}

pluginBundle {
    tags =
        listOf(
            "kotlin",
            "jvm",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.kotlin.jvm") {
            id = "com.javiersc.gradle.plugins.kotlin.jvm"
            displayName = "Kotlin JVM"
            description =
                "A custom plugin for `kotlin(\"jvm\")` plugin with a basic setup"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)
    api(projects.shared.core)

    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
}
