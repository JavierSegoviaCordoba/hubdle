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
            "docs",
            "dokka",
        )
}

gradlePlugin {
    plugins {
        create("com.javiersc.gradle.plugins.changelog") {
            id = "com.javiersc.gradle.plugins.changelog"
            displayName = "Changelog"
            description = "A custom plugin for Changelog plugin with basic setup"
            implementationClass = "com.javiersc.gradle.plugins.changelog.ChangelogPlugin"
        }
    }
}

dependencies {
    api(projects.shared.pluginAccessors)

    api(pluginLibs.jetbrains.intellijPlugins.gradleChangelogPlugin)
    implementation(libs.eclipse.jgit)

    implementation(gradleApi())
    implementation(gradleKotlinDsl())

    testImplementation(projects.shared.coreTest)
    testImplementation(libs.jetbrains.kotlin.kotlinTest)
    testImplementation(libs.kotest.kotestAssertionsCore)
    testImplementation(gradleTestKit())
}
