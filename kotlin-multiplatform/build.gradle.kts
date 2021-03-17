plugins {
    `kotlin-dsl`
    publish
    `accessors-generator`
}

pluginBundle {
    tags =
        listOf(
            "kotlin",
            "multiplatform",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.kotlin.multiplatform") {
            id = "com.javiersc.gradle.plugins.kotlin.multiplatform"
            displayName = "Kotlin Multiplatform"
            description =
                "A custom plugin for `kotlin(\"multiplatform\")` plugin with a basic setup"
        }
    }
}

dependencies {
    implementation(files(pluginLibs.javaClass.protectionDomain.codeSource.location))

    api(projects.accessors)
    api(projects.core)

    api(projects.codeFormatter)

    api(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
}
