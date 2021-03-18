plugins {
    `kotlin-dsl`
    publish
    `plugin-publish`
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
    implementation(files(pluginLibs.javaClass.protectionDomain.codeSource.location))

    api(projects.accessors)

    api(pluginLibs.gitlab.arturboschDetekt.detektGradlePlugin)
}
