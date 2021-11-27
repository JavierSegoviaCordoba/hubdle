plugins {
    `java-gradle-plugin`
    `kotlin-jvm`
    `javiersc-kotlin-library`
    `javiersc-publish`
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
        create("com.javiersc.gradle.plugins.readme.badges.generator") {
            id = "com.javiersc.gradle.plugins.readme.badges.generator"
            displayName = "README Badges Generator"
            description = "Automatically add badges to the root README file"
            implementationClass =
                "com.javiersc.gradle.plugins.readme.badges.generator.ReadmeBadgesGeneratorPlugin"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)

    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
}
