plugins {
    `java-gradle-plugin`
    `kotlin-jvm`
    `javiersc-kotlin-library`
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
        create("com.javiersc.gradle.plugins.kotlin.config") {
            id = "com.javiersc.gradle.plugins.kotlin.config"
            displayName = "Kotlin Config"
            description =
                "A custom plugin which allow creating Android and Kotlin libraries easily."
            implementationClass = "com.javiersc.gradle.plugins.kotlin.config.KotlinConfigPlugin"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)
    api(projects.shared.core)

    implementation(libs.javiersc.kotlin.kotlinStdlib)
    compileOnly(pluginLibs.android.toolsBuild.gradle)
    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
}
