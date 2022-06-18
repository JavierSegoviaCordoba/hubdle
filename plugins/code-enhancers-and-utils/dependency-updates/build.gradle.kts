plugins {
    `javiersc-versioning`
    `java-gradle-plugin`
    `kotlin-jvm`
    `javiersc-kotlin-config`
    `javiersc-publish`
}

pluginBundle {
    tags =
        listOf(
            "dependency updates",
        )
}

gradlePlugin {
    plugins {
        create("com.javiersc.gradle.plugins.dependency.updates") {
            id = "com.javiersc.gradle.plugins.dependency.updates"
            displayName = "Dependency Updates"
            description = "A custom plugin for Ben Manes Versions plugin with basic setup"
            implementationClass =
                "com.javiersc.gradle.plugins.dependency.updates.DependencyUpdatesPlugin"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)

    api(pluginLibs.github.benManes.gradleVersionsPlugin)

    implementation(gradleKotlinDsl())
}
