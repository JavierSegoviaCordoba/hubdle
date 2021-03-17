plugins {
    `kotlin-dsl`
    publish
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
    implementation(files(pluginLibs.javaClass.protectionDomain.codeSource.location))

    api(projects.accessors)
}
