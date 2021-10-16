plugins {
    `kotlin-dsl`
    `javiersc-publish-gradle-plugin`
    `accessors-generator`
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
}
