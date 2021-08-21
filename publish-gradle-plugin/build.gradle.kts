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
        named("com.javiersc.gradle.plugins.publish.gradle.plugin") {
            id = "com.javiersc.gradle.plugins.publish.gradle.plugin"
            displayName = "Publish Gradle Pugin"
            description =
                "A custom plugin for `com.gradle.plugin-publish` plugin with a basic setup"
        }
    }
}

dependencies {
    api(projects.pluginAccessors)
    api(projects.core)
    api(projects.publishingCore)

    implementation(gradleApi())
    api(pluginLibs.gradle.publish.pluginPublishPluginX)
    api(pluginLibs.jetbrains.dokka.dokkaGradlePluginX)
}
