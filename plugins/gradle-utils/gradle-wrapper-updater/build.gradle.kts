plugins {
    `kotlin-dsl`
    `javiersc-publish`
}

pluginBundle {
    tags =
        listOf(
            "wrapper",
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
    api(projects.shared.pluginAccessors)

    implementation(libs.javiersc.kotlin.kotlinStdlib)
    implementation(libs.google.codeGson.gson)
    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
}
