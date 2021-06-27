plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())

    implementation(libs.google.codeGson.gson)
    implementation(libs.javiersc.semanticVersioning.semanticVersioningCore)

    pluginLibs.apply {
        implementation(android.toolsBuild.gradle)
        implementation(ajoberstar.reckon.reckonGradle)
        implementation(diffplug.spotless.spotlessPluginGradle)
        implementation(gradle.publish.pluginPublishPlugin)
        implementation(github.benManes.gradleVersionsPlugin)
        implementation(github.gradleNexus.publishPlugin)
        implementation(gitlab.arturboschDetekt.detektGradlePlugin)
        implementation(jetbrains.dokka.dokkaGradlePlugin)
        implementation(jetbrains.intellijPlugins.gradleChangelogPlugin)
        implementation(jetbrains.kotlin.kotlinGradlePlugin)
        implementation(jetbrains.kotlinx.binaryCompatibilityValidator)
        implementation(vyarus.gradleMkdocsPlugin)
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
