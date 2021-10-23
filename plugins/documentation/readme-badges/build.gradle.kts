plugins {
    `kotlin-dsl`
    `javiersc-publish-gradle-plugin`
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
    api(projects.shared.pluginAccessors)

    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePluginX)
}
