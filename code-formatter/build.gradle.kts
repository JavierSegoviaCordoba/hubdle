plugins {
    `kotlin-dsl`
    publish
    `plugin-publish`
    `accessors-generator`
}

pluginBundle {
    tags =
        listOf(
            "code formatter",
            "format",
            "ktfmt",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.code.formatter") {
            id = "com.javiersc.gradle.plugins.code.formatter"
            displayName = "Code Formatter"
            description = "A custom plugin for Spotless Plugin with basic setup based on ktfmt"
        }
    }
}

dependencies {
    implementation(files(pluginLibs.javaClass.protectionDomain.codeSource.location))

    api(projects.accessors)

    api(pluginLibs.diffplug.spotless.spotlessPluginGradle)
    api(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
}
