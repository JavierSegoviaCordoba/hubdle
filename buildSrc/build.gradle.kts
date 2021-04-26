
plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    jcenter()
    google()
    gradlePluginPortal()
}

dependencies {
    /**
     * ToDo: workaround until accessors are available
     * (https://github.com/gradle/gradle/issues/15383) When it is fixed, remove both implementations
     * and remove `VersionCatalogExtension.kt` file
     */
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(files(pluginLibs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(gradleApi())
    implementation(localGroovy())

    pluginLibs.apply {
        implementation(android.toolsBuild.gradle)
        implementation(ajoberstar.reckon.reckonGradle)
        implementation(diffplug.spotless.spotlessPluginGradle)
        implementation(gradle.publish.pluginPublishPlugin)
        implementation(github.benManes.gradleVersionsPlugin)
        implementation(github.gradleNexus.publishPlugin)
        implementation(gitlab.arturboschDetekt.detektGradlePlugin)
        implementation(jetbrains.dokka.dokkaCore)
        implementation(jetbrains.dokka.dokkaGradlePlugin)
        implementation(jetbrains.intellijPlugins.gradleChangelogPlugin)
        implementation(jetbrains.kotlin.kotlinGradlePlugin)
        implementation(jetbrains.kotlin.kotlinSerialization)
        implementation(jetbrains.kotlinx.binaryCompatibilityValidator)
    }
}
