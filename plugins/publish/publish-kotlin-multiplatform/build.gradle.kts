plugins {
    `kotlin-dsl`
    `javiersc-publish-gradle-plugin`
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
        named("com.javiersc.gradle.plugins.publish.kotlin.multiplatform") {
            id = "com.javiersc.gradle.plugins.publish.kotlin.multiplatform"
            displayName = "Publish Kotlin Multiplatform"
            description =
                "A custom plugin for `maven-publish` and `kotlin-multiplatform` plugin with a basic setup"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)
    api(projects.shared.core)
    api(projects.shared.publishingCore)

    implementation(gradleApi())
}
