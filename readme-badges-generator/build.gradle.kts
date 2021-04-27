plugins {
    `kotlin-dsl`
    publish
    `plugin-publish`
    `accessors-generator`
}

pluginBundle {
    tags =
        listOf(
            "README",
            "badges",
            "generator",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.readme.badges.generator") {
            id = "com.javiersc.gradle.plugins.readme.badges.generator"
            displayName = "README Badges Generator"
            description = "Automatically add badges to the root README file"
        }
    }
}

dependencies {
    /**
     * ToDo: workaround until accessors are available
     * (https://github.com/gradle/gradle/issues/15383) When it is fixed, remove both implementations
     * and remove `VersionCatalogExtension.kt` file
     */
    implementation(files(libs.javaClass.protectionDomain.codeSource.location))
    implementation(files(pluginLibs.javaClass.protectionDomain.codeSource.location))

    implementation(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
    api(projects.accessors)
}
