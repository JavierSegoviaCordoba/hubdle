plugins {
    `kotlin-dsl`
    `javiersc-publish-gradle-plugin`
    `accessors-generator`
}

pluginBundle {
    tags =
        listOf(
            "gradle",
            "grapper",
            "updater",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.gradle.wrapper.updater") {
            id = "com.javiersc.gradle.plugins.gradle.wrapper.updater"
            displayName = "Gradle Wrapper Updater"
            description = "A plugin for updating the Gradle Wrapper"
        }
    }
}

dependencies {
    api(projects.pluginAccessors)
    api(projects.core)

    implementation(libs.google.codeGson.gson)
    implementation(pluginLibs.jetbrains.kotlin.kotlinGradlePluginX)
}
