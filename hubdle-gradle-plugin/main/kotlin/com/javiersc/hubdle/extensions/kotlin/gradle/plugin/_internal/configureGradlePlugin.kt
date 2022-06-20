package com.javiersc.hubdle.extensions.kotlin.gradle.plugin._internal

import com.gradle.publish.PluginBundleExtension
import com.javiersc.gradle.properties.extensions.getProperty
import com.javiersc.hubdle.HubdleProperty
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.HubdleState
import com.javiersc.hubdle.extensions._internal.state.catalogImplementation
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.dependencies._internal.COM_JAVIERSC_GRADLE_GRADLE_EXTENSIONS_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.COM_JAVIERSC_GRADLE_GRADLE_TEST_EXTENSIONS_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_CORE_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_TEST_MODULE
import com.javiersc.hubdle.extensions.kotlin._internal.configJvmTarget
import com.javiersc.hubdle.extensions.kotlin.tools.explicit.api._internal.configureExplicitApi
import com.javiersc.hubdle.extensions.options.configureDefaultJavaSourceSets
import com.javiersc.hubdle.extensions.options.configureDefaultKotlinSourceSets
import com.javiersc.hubdle.extensions.options.configureJavaJarsForPublishing
import com.javiersc.hubdle.extensions.options.configureMavenPublication
import com.javiersc.hubdle.extensions.options.configurePublishingExtension
import com.javiersc.hubdle.extensions.options.configureSigningForPublishing
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.gradleKotlinDsl
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

internal fun configureGradlePlugin(project: Project) {
    if (project.hubdleState.kotlin.gradle.plugin.isEnabled) {
        project.pluginManager.apply(PluginIds.Gradle.javaGradlePlugin)
        project.pluginManager.apply(PluginIds.Kotlin.jvm)

        project.configureExplicitApi()
        project.configJvmTarget()
        project.the<JavaPluginExtension>().configureDefaultJavaSourceSets()
        project.the<KotlinProjectExtension>().configureDefaultKotlinSourceSets()
        project.the<KotlinJvmProjectExtension>().configureGradleDependencies()

        if (project.hubdleState.kotlin.isPublishingEnabled) {
            project.pluginManager.apply(PluginIds.Publishing.mavenPublish)
            project.pluginManager.apply(PluginIds.Publishing.signing)
            project.pluginManager.apply(PluginIds.Publishing.gradlePluginPublish)
            project.configureJavaJarsForPublishing()
            project.configurePublishingExtension()
            project.configureMavenPublication("java")
            project.configureSigningForPublishing()
            project.configure<PluginBundleExtension> {
                website = project.getProperty(HubdleProperty.POM.url)
                vcsUrl = project.getProperty(HubdleProperty.POM.scmUrl)
            }
        }
    }
}

private fun KotlinJvmProjectExtension.configureGradleDependencies() {
    sourceSets.named("main") { it.dependencies { configureMainDependencies() } }
    sourceSets.named("test") { it.dependencies { configureTestDependencies() } }
}

private val KotlinDependencyHandler.gradlePluginFeatures: HubdleState.Kotlin.Gradle.Plugin.Features
    get() = project.hubdleState.kotlin.gradle.plugin.features

private fun KotlinDependencyHandler.configureMainDependencies() {
    implementation(project.dependencies.gradleApi())
    implementation(project.gradleKotlinDsl())

    if (gradlePluginFeatures.javierScStdlib) {
        catalogImplementation(COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE)
    }
    if (gradlePluginFeatures.javierScGradleExtensions) {
        catalogImplementation(COM_JAVIERSC_GRADLE_GRADLE_EXTENSIONS_MODULE)
    }
    if (gradlePluginFeatures.coroutines) {
        catalogImplementation(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_CORE_MODULE)
    }
}

private fun KotlinDependencyHandler.configureTestDependencies() {
    implementation(project.dependencies.gradleTestKit())

    if (gradlePluginFeatures.javierScGradleExtensions) {
        catalogImplementation(COM_JAVIERSC_GRADLE_GRADLE_TEST_EXTENSIONS_MODULE)
    }
    if (gradlePluginFeatures.coroutines) {
        catalogImplementation(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_TEST_MODULE)
    }
}
