plugins {
    `kotlin-dsl`
    `javiersc-publish-gradle-plugin`
    `accessors-generator`
}

pluginBundle {
    tags =
        listOf(
            "code analysis",
            "detekt",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.code.analysis") {
            id = "com.javiersc.gradle.plugins.code.analysis"
            displayName = "Code Analysis"
            description = "A custom plugin for Detekt Plugin with basic setup"
        }
    }
}

dependencies {
    api(projects.projects.pluginAccessors)

    api(pluginLibs.gitlab.arturboschDetekt.detektGradlePluginX)
}
