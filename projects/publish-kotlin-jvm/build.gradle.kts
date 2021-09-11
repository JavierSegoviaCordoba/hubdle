plugins {
    `kotlin-dsl`
    `javiersc-publish-gradle-plugin`
    `accessors-generator`
}

pluginBundle {
    tags =
        listOf(
            "publish",
            "maven",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.publish.kotlin.jvm") {
            id = "com.javiersc.gradle.plugins.publish.kotlin.jvm"
            displayName = "Publish Kotlin JVM"
            description =
                "A custom plugin for `maven-publish` and `kotlin-jvm` plugin with a basic setup"
        }
    }
}

dependencies {
    api(projects.projects.pluginAccessors)
    api(projects.projects.core)
    api(projects.projects.publishingCore)

    implementation(gradleApi())
    api(pluginLibs.jetbrains.dokka.dokkaGradlePluginX)
}
