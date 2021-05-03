plugins {
    `kotlin-dsl`
    publish
    `plugin-publish`
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
    api(projects.accessors)
    api(projects.core)

    implementation(gradleApi())
    api(pluginLibs.jetbrains.dokka.dokkaGradlePlugin)
}
