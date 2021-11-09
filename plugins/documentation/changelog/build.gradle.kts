plugins {
    `kotlin-dsl`
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
        named("com.javiersc.gradle.plugins.changelog") {
            id = "com.javiersc.gradle.plugins.changelog"
            displayName = "Changelog"
            description = "A custom plugin for Changelog plugin with basic setup"
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
