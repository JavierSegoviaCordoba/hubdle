
plugins {
    `kotlin-dsl`
    publish
    `plugin-publish`
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
    api(projects.accessors)

    api(pluginLibs.diffplug.spotless.spotlessPluginGradle)
    implementation(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)
}
