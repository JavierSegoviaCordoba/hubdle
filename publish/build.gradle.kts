plugins {
    `kotlin-dsl`
    publish
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
        named("com.javiersc.gradle.plugins.publish") {
            id = "com.javiersc.gradle.plugins.publish"
            displayName = "Publish"
            description = "A custom plugin for `maven-publish` plugin with a basic setup"
        }
    }
}

dependencies {
    implementation(files(pluginLibs.javaClass.protectionDomain.codeSource.location))

    api(projects.accessors)
    implementation(projects.core)

    implementation(gradleApi())
    api(pluginLibs.jetbrains.dokka.dokkaGradlePlugin)
}
