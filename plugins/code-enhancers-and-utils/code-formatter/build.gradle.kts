plugins {
    `kotlin-dsl`
    `javiersc-publish-gradle-plugin`
}

pluginBundle {
    tags =
        listOf(
            "code formatter",
            "format",
            "ktfmt",
        )
}

gradlePlugin {
    plugins {
        named("com.javiersc.gradle.plugins.code.formatter") {
            id = "com.javiersc.gradle.plugins.code.formatter"
            displayName = "Code Formatter"
            description = "A custom plugin for Spotless Plugin with basic setup based on ktfmt"
        }
    }
}

file("src/main/kotlin/KtfmtVersion.kt").apply {
    if (!exists()) createNewFile()
    writeText(
        """
            |internal const val KTFMT_VERSION: String = "${libs.versions.ktfmt.get()}"
            |
        """.trimMargin(),
    )
}

dependencies {
    api(projects.shared.pluginAccessors)

    implementation(pluginLibs.diffplug.spotless.spotlessPluginGradle)
    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePluginX)

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
