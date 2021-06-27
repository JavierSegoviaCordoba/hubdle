plugins {
    `kotlin-dsl`
    publish
    `plugin-publish`
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
    api(projects.accessors)
    api(projects.core)

    api(pluginLibs.jetbrains.dokka.dokkaGradlePlugin)
    implementation(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
    api(pluginLibs.vyarus.gradleMkdocsPlugin)
}
