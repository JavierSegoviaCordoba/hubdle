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

    // TODO: remove when the next issue is fixed:
    //  https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(files(pluginLibs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(libs.google.codeGson.gson)
    implementation(libs.jsoup.jsoup)
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
