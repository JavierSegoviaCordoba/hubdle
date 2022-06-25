plugins {
    `java-gradle-plugin`
    `kotlin-jvm`
    `javiersc-kotlin-config`
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

val testPluginClasspath: Configuration by configurations.creating

dependencies {
    api(projects.shared.pluginAccessors)

    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)

    implementation(gradleKotlinDsl())
    implementation(libs.javiersc.kotlin.kotlinStdlib)
    implementation(projects.shared.core)

    // README Kotlin badge has always the next version
    testPluginClasspath("${pluginLibs.jetbrains.kotlin.kotlinGradlePlugin.get().module}:1.5.32")

    testImplementation(gradleTestKit())
    testImplementation(projects.shared.coreTest)
    testImplementation(libs.jetbrains.kotlin.kotlinTest)
    testImplementation(libs.kotest.kotestAssertionsCore)
}

tasks {
    pluginUnderTestMetadata {
        pluginClasspath.from(testPluginClasspath)
    }
}
