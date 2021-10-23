plugins {
    `kotlin-dsl`
    `javiersc-publish-gradle-plugin`
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

dependencies {
    api(projects.shared.pluginAccessors)
    api(projects.shared.core)

    implementation(pluginLibs.jetbrains.dokka.dokkaGradlePluginX)
    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePluginX)
    implementation(pluginLibs.vyarus.gradleMkdocsPluginX)

    implementation(gradleApi())
    implementation(gradleKotlinDsl())

    testImplementation(gradleTestKit())
    testImplementation(projects.shared.coreTest)
    testImplementation(libs.jetbrains.kotlin.kotlinTest)
    testImplementation(libs.jetbrains.kotlin.kotlinTestJunit)
    testImplementation(libs.kotest.kotestAssertionsCore)
}

tasks{
    pluginUnderTestMetadata {
        dependencies {
            implementation(pluginLibs.jetbrains.kotlin.kotlinGradlePluginX)
        }
    }
}
