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
    implementation(files(pluginLibs.javaClass.protectionDomain.codeSource.location))

    api(projects.accessors)

    api(pluginLibs.github.gradleNexus.publishPlugin)
}
