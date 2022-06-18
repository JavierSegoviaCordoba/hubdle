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
            "code coverage",
            "kover",
        )
}

gradlePlugin {
    plugins {
        create("com.javiersc.gradle.plugins.code.coverage") {
            id = "com.javiersc.gradle.plugins.code.coverage"
            displayName = "Code Coverage"
            description = "A custom plugin for Kover Plugin with basic setup"
            implementationClass = "com.javiersc.gradle.plugins.code.coverage.CodeCoveragePlugin"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)

    api(pluginLibs.jetbrains.kotlinx.kover)
}
