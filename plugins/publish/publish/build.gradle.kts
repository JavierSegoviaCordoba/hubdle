plugins {
    `java-gradle-plugin`
    `kotlin-jvm`
    `javiersc-kotlin-config`
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
        create("com.javiersc.gradle.plugins.publish") {
            id = "com.javiersc.gradle.plugins.publish"
            displayName = "Publish all type of projects"
            description =
                "Publish Kotlin (JVM, Multiplatform) libraries, Android libraries, Gradle Plugins, and Version Catalogs"
            implementationClass = "com.javiersc.gradle.plugins.publish.PublishPlugin"
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
