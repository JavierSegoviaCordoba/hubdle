plugins {
    `kotlin-dsl`
    `javiersc-publish`
}

pluginBundle {
    tags =
        listOf(
            "publish",
            "maven",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.publish") {
            id = "com.javiersc.gradle.plugins.publish"
            displayName = "Publish all type of projects"
            description =
                "Publish Kotlin (JVM, Multiplatform) libraries, Android libraries, Gradle Plugins, and Version Catalogs"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)
    api(projects.shared.core)

    compileOnly(pluginLibs.android.toolsBuild.gradle)
    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)

    implementation(gradleApi())
    implementation(gradleKotlinDsl())
    implementation(libs.javiersc.kotlin.kotlinStdlib)
    implementation(pluginLibs.github.tripletGradle.playPublisher)
    implementation(pluginLibs.gradle.publish.pluginPublishPlugin)
}
