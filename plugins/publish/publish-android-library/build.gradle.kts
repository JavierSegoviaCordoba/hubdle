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
        named("com.javiersc.gradle.plugins.publish.android.library") {
            id = "com.javiersc.gradle.plugins.publish.android.library"
            displayName = "Publish Android Library"
            description = "A custom plugin for `maven-publish` and `android-library` plugin with a basic setup"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)
    api(projects.shared.core)
    api(projects.shared.publishingCore)

    implementation(gradleApi())
    compileOnly(pluginLibs.android.toolsBuild.gradle)
    implementation(pluginLibs.jetbrains.dokka.dokkaGradlePluginX)
}
