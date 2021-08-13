plugins {
    `kotlin-dsl`
    publish
    `plugin-publish`
    `accessors-generator`
}

pluginBundle {
    tags =
        listOf(
            "gradle",
            "versions catalogs",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.build.version.catalogs.updater") {
            id = "com.javiersc.gradle.plugins.build.version.catalogs.updater"
            displayName = "Build Version Catalogs"
            description = "A plugin for updating Build Version Catalogs"
        }
    }
}

dependencies {
    api(projects.pluginAccessors)
    api(projects.core)

    implementation(libs.jsoup.jsoup)
    implementation(libs.javiersc.semanticVersioning.semanticVersioningCore)

    implementation(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
}
