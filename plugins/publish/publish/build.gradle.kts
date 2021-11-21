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

    implementation(gradleApi())
    implementation(gradleKotlinDsl())
    implementation(libs.javiersc.kotlin.kotlinStdlib)
    compileOnly(pluginLibs.android.toolsBuild.gradle)
    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
    implementation(pluginLibs.gradle.publish.pluginPublishPlugin)
}
