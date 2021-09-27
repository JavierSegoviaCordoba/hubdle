plugins {
    `kotlin-dsl`
    `javiersc-publish-gradle-plugin`
    `accessors-generator`
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
    api(projects.projects.pluginAccessors)
    api(projects.projects.core)

    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePluginX)
    implementation(pluginLibs.jetbrains.kotlin.kotlinGradlePluginApi)
}
