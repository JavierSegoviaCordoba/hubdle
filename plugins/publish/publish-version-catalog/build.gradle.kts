plugins {
    `kotlin-dsl`
    `javiersc-publish-gradle-plugin`
    `accessors-generator`
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
        named("com.javiersc.gradle.plugins.publish.version.catalog") {
            id = "com.javiersc.gradle.plugins.publish.version.catalog"
            displayName = "Publish Version Catalog"
            description =
                "A custom plugin for `maven-publish` and `version-catalog` plugin with a basic setup"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)
    api(projects.shared.core)
    api(projects.shared.publishingCore)

    implementation(gradleApi())
}
