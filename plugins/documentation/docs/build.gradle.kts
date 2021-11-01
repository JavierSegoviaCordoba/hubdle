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
        named("com.javiersc.gradle.plugins.docs") {
            id = "com.javiersc.gradle.plugins.docs"
            displayName = "Docs"
            description = "A custom plugin for Dokka Plugin with basic setup"
        }
    }
}

val testPluginClasspath: Configuration by configurations.creating

dependencies {
    api(projects.shared.pluginAccessors)

    implementation(pluginLibs.jetbrains.dokka.dokkaGradlePlugin)
    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
    implementation(pluginLibs.vyarus.gradleMkdocsPlugin)

    implementation(gradleApi())
    implementation(gradleKotlinDsl())

    testPluginClasspath(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)

    testImplementation(gradleTestKit())
    testImplementation(projects.shared.coreTest)
    testImplementation(libs.jetbrains.kotlin.kotlinTest)
    testImplementation(libs.jetbrains.kotlin.kotlinTestJunit)
    testImplementation(libs.kotest.kotestAssertionsCore)
}

tasks {
    pluginUnderTestMetadata {
        pluginClasspath.from(testPluginClasspath)
    }
}
