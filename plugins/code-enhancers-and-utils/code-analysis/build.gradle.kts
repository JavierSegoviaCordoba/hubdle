plugins {
    `java-gradle-plugin`
    `kotlin-jvm`
    `javiersc-kotlin-config`
    `javiersc-publish`
}

pluginBundle {
    tags =
        listOf(
            "code analysis",
            "detekt",
        )
}

gradlePlugin {
    plugins {
        create("com.javiersc.gradle.plugins.code.analysis") {
            id = "com.javiersc.gradle.plugins.code.analysis"
            displayName = "Code Analysis"
            description = "A custom plugin for Detekt Plugin with basic setup"
            implementationClass = "com.javiersc.gradle.plugins.code.analysis.CodeAnalysisPlugin"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)

    api(pluginLibs.gitlab.arturboschDetekt.detektGradlePlugin)
    api(pluginLibs.sonarqube.scannerGradle.sonarqubeGradlePlugin)
    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)

    implementation(gradleKotlinDsl())
}
