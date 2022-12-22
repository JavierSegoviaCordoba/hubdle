package com.javiersc.hubdle.extensions._internal

internal enum class PluginId(val id: String) {
    AdarshrTestLogger("com.adarshr.test-logger"),
    AndroidApplication("com.android.application"),
    AndroidLibrary("com.android.library"),
    Detekt("io.gitlab.arturbosch.detekt"),
    DiffplugSpotless("com.diffplug.spotless"),
    GithubGradleNexusPublishPlugin("io.github.gradle-nexus.publish-plugin"),
    GradleApplication("org.gradle.application"),
    GradlePluginPublish("com.gradle.plugin-publish"),
    GradleSigning("org.gradle.signing"),
    GradleVersionCatalog("org.gradle.version-catalog"),
    JavaGradlePlugin("java-gradle-plugin"),
    JavaTestFixtures("java-test-fixtures"),
    JavierscSemverGradlePlugin("com.javiersc.semver.gradle.plugin"),
    JetbrainsChangelog("org.jetbrains.changelog"),
    JetbrainsCompose("org.jetbrains.compose"),
    JetbrainsDokka("org.jetbrains.dokka"),
    JetbrainsIntellij("org.jetbrains.intellij"),
    JetbrainsKotlinAndroid("org.jetbrains.kotlin.android"),
    JetbrainsKotlinJvm("org.jetbrains.kotlin.jvm"),
    JetbrainsKotlinMultiplatform("org.jetbrains.kotlin.multiplatform"),
    JetbrainsKotlinPluginSerialization("org.jetbrains.kotlin.plugin.serialization"),
    JetbrainsKotlinxBinaryCompatibilityValidator(KotlinBinaryCompatibilityValidator),
    JetbrainsKotlinxKover("org.jetbrains.kotlinx.kover"),
    MavenPublish("org.gradle.maven-publish"),
    Sonarqube("org.sonarqube"),
    VyarusMkdocsBuild("ru.vyarus.mkdocs-build"),
    ;

    override fun toString(): String = id
}

private const val KotlinBinaryCompatibilityValidator =
    "org.jetbrains.kotlinx.binary-compatibility-validator"
