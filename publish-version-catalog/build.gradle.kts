plugins {
    `kotlin-dsl`
    publish
    `plugin-publish`
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
    api(projects.pluginAccessors)
    api(projects.core)
    api(projects.publishingCore)

    implementation(gradleApi())
    api(pluginLibs.jetbrains.dokka.dokkaGradlePlugin)
}
