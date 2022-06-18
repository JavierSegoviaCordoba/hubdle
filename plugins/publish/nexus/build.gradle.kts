plugins {
    `javiersc-versioning`
    `java-gradle-plugin`
    `kotlin-jvm`
    `javiersc-kotlin-config`
    `javiersc-publish`
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
        create("com.javiersc.gradle.plugins.nexus") {
            id = "com.javiersc.gradle.plugins.nexus"
            displayName = "Nexus"
            description =
                "A custom plugin for `io.github.gradle-nexus.publish-plugin` plugin with a basic setup"
            implementationClass = "com.javiersc.gradle.plugins.nexus.NexusPlugin"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)

    api(pluginLibs.github.gradleNexus.publishPlugin)
}
