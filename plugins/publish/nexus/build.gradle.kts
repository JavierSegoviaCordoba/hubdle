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
            "nexus",
            "MavenCentral",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.nexus") {
            id = "com.javiersc.gradle.plugins.nexus"
            displayName = "Nexus"
            description =
                "A custom plugin for `io.github.gradle-nexus.publish-plugin` plugin with a basic setup"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)

    api(pluginLibs.github.gradleNexus.publishPluginX)
}
