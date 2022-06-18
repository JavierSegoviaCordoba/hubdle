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
            "code formatter",
            "format",
            "ktfmt",
        )
}

gradlePlugin {
    plugins {
        create("com.javiersc.gradle.plugins.code.formatter") {
            id = "com.javiersc.gradle.plugins.code.formatter"
            displayName = "Code Formatter"
            description = "A custom plugin for Spotless Plugin with basic setup based on ktfmt"
            implementationClass = "com.javiersc.gradle.plugins.code.formatter.CodeFormatterPlugin"
        }
    }
}

file("main/kotlin/com/javiersc/gradle/plugins/code/formatter/KtfmtVersion.kt").apply {
    if (!exists()) createNewFile()
    writeText(
        """
            |package com.javiersc.gradle.plugins.code.formatter
            |
            |internal const val KTFMT_VERSION: String = "${libs.versions.ktfmt.get()}"
            |
        """.trimMargin(),
    )
}

val testPluginClasspath: Configuration by configurations.creating

dependencies {
    api(projects.shared.pluginAccessors)

    compileOnly(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)

    implementation(gradleKotlinDsl())
    implementation(pluginLibs.diffplug.spotless.spotlessPluginGradle)

    testPluginClasspath(pluginLibs.jetbrains.kotlin.kotlinGradlePlugin)

    testImplementation(gradleTestKit())
    testImplementation(projects.shared.coreTest)
    testImplementation(libs.jetbrains.kotlin.kotlinTest)
    testImplementation(libs.kotest.kotestAssertionsCore)
}

tasks { pluginUnderTestMetadata { pluginClasspath.from(testPluginClasspath) } }
