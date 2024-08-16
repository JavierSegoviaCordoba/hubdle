package com.javiersc.hubdle.project.extensions.shared

public enum class PluginId(public val id: String) {
    AdarshrTestLogger("com.adarshr.test-logger"),
    AndroidApplication("com.android.application"),
    AndroidLibrary("com.android.library"),
    Detekt("io.gitlab.arturbosch.detekt"),
    DiffplugSpotless("com.diffplug.spotless"),
    GithubGradleNexusPublishPlugin("io.github.gradle-nexus.publish-plugin"),
    GradleApplication("org.gradle.application"),
    GradleJava("org.gradle.java"),
    GradlePluginPublish("com.gradle.plugin-publish"),
    GradleSigning("org.gradle.signing"),
    GradleVersionCatalog("org.gradle.version-catalog"),
    JavaGradlePlugin("java-gradle-plugin"),
    JavaTestFixtures("java-test-fixtures"),
    JavierscHubdle("com.javiersc.hubdle"),
    JavierscSemverGradlePlugin("com.javiersc.semver"),
    JetbrainsChangelog("org.jetbrains.changelog"),
    JetbrainsCompose("org.jetbrains.compose"),
    JetbrainsDokka("org.jetbrains.dokka"),
    JetbrainsIntellij("org.jetbrains.intellij"),
    JetbrainsKotlinAndroid("org.jetbrains.kotlin.android"),
    JetbrainsKotlinJvm("org.jetbrains.kotlin.jvm"),
    JetbrainsKotlinMultiplatform("org.jetbrains.kotlin.multiplatform"),
    JetbrainsKotlinPluginCompose("org.jetbrains.kotlin.plugin.compose"),
    JetbrainsKotlinPluginSerialization("org.jetbrains.kotlin.plugin.serialization"),
    JetbrainsKotlinxBinaryCompatibilityValidator(KotlinBinaryCompatibilityValidator),
    JetbrainsKotlinxKover("org.jetbrains.kotlinx.kover"),
    MavenPublish("org.gradle.maven-publish"),
    Sonarqube("org.sonarqube"),
    SqlDelight("app.cash.sqldelight"),
    VyarusMkdocsBuild("ru.vyarus.mkdocs-build"),
    ;

    override fun toString(): String = id
}

private const val KotlinBinaryCompatibilityValidator =
    "org.jetbrains.kotlinx.binary-compatibility-validator"
