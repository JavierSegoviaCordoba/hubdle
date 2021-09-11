plugins {
    `kotlin-dsl`
    `javiersc-publish-gradle-plugin`
    `accessors-generator`
}

pluginBundle {
    tags =
        listOf(
            "docs",
            "dokka",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.docs") {
            id = "com.javiersc.gradle.plugins.docs"
            displayName = "Docs"
            description = "A custom plugin for Dokka Plugin with basic setup"
        }
    }
}

dependencies {
    api(projects.projects.pluginAccessors)
    api(projects.projects.core)

    api(pluginLibs.jetbrains.dokka.dokkaGradlePluginX)
    implementation(pluginLibs.jetbrains.kotlin.kotlinGradlePluginX)
    api(pluginLibs.vyarus.gradleMkdocsPluginX)
}
