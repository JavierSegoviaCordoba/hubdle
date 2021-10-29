plugins {
    `kotlin-dsl`
    `javiersc-publish`
}

pluginBundle {
    tags =
        listOf(
            "kotlin",
            "library",
            "jvm",
            "android",
            "multiplatform",
        )
}

gradlePlugin {
    plugins {
        create("com.javiersc.gradle.plugins.kotlin.library") {
            id = "com.javiersc.gradle.plugins.kotlin.library"
            displayName = "Kotlin Library"
            description =
                "A custom plugin which allow creating Android and Kotlin libraries easily."
            implementationClass = "com.javiersc.gradle.plugins.KotlinLibraryPlugin"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)
    api(projects.shared.core)

    compileOnly(pluginLibs.android.toolsBuild.gradle)
    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
}
