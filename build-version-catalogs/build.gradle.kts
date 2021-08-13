plugins {
    `kotlin-dsl`
    publish
    `plugin-publish`
    `accessors-generator`
}

pluginBundle {
    tags =
        listOf(
            "Version Catalogs",
            "dependabot",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.build.version.catalogs") {
            id = "com.javiersc.gradle.plugins.build.version.catalogs"
            displayName = "Build Version Catalogs"
            description =
                "Build Version Catalogs from a Gradle files which allow dependabot compatibility"
        }
    }
}

dependencies {
    api(projects.pluginAccessors)
    api(projects.core)
}
