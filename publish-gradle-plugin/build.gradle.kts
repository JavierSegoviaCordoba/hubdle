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
        named("com.javiersc.gradle.plugins.publish.gradle.plugin") {
            id = "com.javiersc.gradle.plugins.publish.gradle.plugin"
            displayName = "Publish Gradle Pugin"
            description =
                "A custom plugin for `com.gradle.plugin-publish` plugin with a basic setup"
        }
    }
}

dependencies {
    api(projects.accessors)
    api(projects.core)

    implementation(gradleApi())
    api(pluginLibs.gradle.publish.pluginPublishPlugin)
    api(pluginLibs.jetbrains.dokka.dokkaGradlePlugin)
}
