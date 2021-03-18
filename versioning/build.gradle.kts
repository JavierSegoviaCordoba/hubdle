plugins {
    `kotlin-dsl`
    publish
    `plugin-publish`
    `accessors-generator`
}

pluginBundle {
    tags =
        listOf(
            "versioning",
            "reckon",
            "git",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.versioning") {
            id = "com.javiersc.gradle.plugins.versioning"
            displayName = "Versioning"
            description = "A custom plugin for Reckon Plugin and its git versioning"
        }
    }
}

dependencies {
    implementation(files(pluginLibs.javaClass.protectionDomain.codeSource.location))

    api(projects.accessors)
    api(projects.core)

    api(pluginLibs.ajoberstar.reckon.reckonGradle)
}
