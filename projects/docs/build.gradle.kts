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

    implementation(pluginLibs.jetbrains.dokka.dokkaGradlePluginX)
    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePluginX)
    implementation(pluginLibs.jetbrains.kotlin.kotlinGradlePluginApi)
    implementation(pluginLibs.vyarus.gradleMkdocsPluginX)
}
