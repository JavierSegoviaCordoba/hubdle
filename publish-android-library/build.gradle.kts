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
        named("com.javiersc.gradle.plugins.publish.android.library") {
            id = "com.javiersc.gradle.plugins.publish.android.library"
            displayName = "Publish Android Library"
            description = "A custom plugin for `maven-publish` and `android-library` plugin with a basic setup"
        }
    }
}

dependencies {
    implementation(files(pluginLibs.javaClass.protectionDomain.codeSource.location))

    api(projects.accessors)
    api(projects.core)

    implementation(gradleApi())
    api(pluginLibs.android.toolsBuild.gradle)
    api(pluginLibs.jetbrains.dokka.dokkaGradlePlugin)
}
