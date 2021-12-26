plugins {
    `java-gradle-plugin`
    `kotlin-jvm`
    `javiersc-kotlin-library`
    `javiersc-publish`
}

pluginBundle {
    tags =
        listOf(
            "default config",
            "allprojects",
        )
}

gradlePlugin {
    plugins {
        create("com.javiersc.gradle.plugins.all.projects") {
            id = "com.javiersc.gradle.plugins.all.projects"
            displayName = "All Projects"
            description = "A custom plugin which a default config for all projects"
            implementationClass = "com.javiersc.gradle.plugins.all.projects.AllProjectsPlugin"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)

    implementation(pluginLibs.adarshr.gradleTestLoggerPlugin)
}
