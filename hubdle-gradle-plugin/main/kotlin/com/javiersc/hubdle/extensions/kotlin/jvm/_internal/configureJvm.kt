package com.javiersc.hubdle.extensions.kotlin.jvm._internal

import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.HubdleState
import com.javiersc.hubdle.extensions._internal.state.catalogDependency as catalogDep
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.explicit.api._internal.configureExplicitApi
import com.javiersc.hubdle.extensions.dependencies._internal.constants.COM_JAVIERSC_GRADLE_GRADLE_EXTENSIONS_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.COM_JAVIERSC_GRADLE_GRADLE_TEST_EXTENSIONS_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.IO_KOTEST_KOTEST_ASSERTIONS_CORE_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_CORE_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_TEST_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_CORE_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_JSON_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_MODULE
import com.javiersc.hubdle.extensions.kotlin._internal.configJvmTarget
import com.javiersc.hubdle.extensions.options.configureDefaultJavaSourceSets
import com.javiersc.hubdle.extensions.options.configureDefaultKotlinSourceSets
import com.javiersc.hubdle.extensions.options.configureJavaJarsForPublishing
import com.javiersc.hubdle.extensions.options.configureMavenPublication
import com.javiersc.hubdle.extensions.options.configurePublishingExtension
import com.javiersc.hubdle.extensions.options.configureSigningForPublishing
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.gradleKotlinDsl
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

internal fun configureJvm(project: Project) {
    if (project.hubdleState.kotlin.jvm.isEnabled) {
        project.pluginManager.apply(PluginIds.Kotlin.jvm)

        project.configureExplicitApi()
        project.configJvmTarget()
        project.the<JavaPluginExtension>().configureDefaultJavaSourceSets()
        project.the<KotlinProjectExtension>().configureDefaultKotlinSourceSets()
        project.the<KotlinJvmProjectExtension>().configureJvmDependencies()

        project.hubdleState.kotlin.jvm.application?.let {
            project.pluginManager.apply(PluginIds.Gradle.application)
            it.execute(project.the())
        }

        if (project.hubdleState.config.publishing.isEnabled) {
            project.pluginManager.apply(PluginIds.Publishing.mavenPublish)
            project.configurePublishingExtension()
            project.configureMavenPublication("java")
            project.configureJavaJarsForPublishing()
            project.configureSigningForPublishing()
        }
    }
}

internal fun configureKotlinJvmRawConfig(project: Project) {
    project.hubdleState.kotlin.jvm.rawConfig.kotlin?.execute(project.the())
}

private fun KotlinJvmProjectExtension.configureJvmDependencies() {
    sourceSets.named("main") { set -> set.dependencies { configureMainDependencies() } }
    sourceSets.named("test") { set -> set.dependencies { configureTestDependencies() } }
}

internal val Project.jvmFeatures: HubdleState.Kotlin.Jvm.Features
    get() = hubdleState.kotlin.jvm.features

private fun KotlinDependencyHandler.configureMainDependencies() {
    with(project) {
        if (jvmFeatures.coroutines) {
            implementation(catalogDep(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_CORE_MODULE))
        }
        if (jvmFeatures.extendedGradle) {
            implementation(project.dependencies.gradleApi())
            implementation(project.dependencies.gradleTestKit())
            implementation(project.gradleKotlinDsl())
            implementation(catalogDep(COM_JAVIERSC_GRADLE_GRADLE_EXTENSIONS_MODULE))
        }
        if (jvmFeatures.extendedStdlib) {
            implementation(catalogDep(COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE))
        }

        if (jvmFeatures.serialization.isEnabled) {
            project.pluginManager.apply(PluginIds.Kotlin.serialization)
            implementation(catalogDep(ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_CORE_MODULE))
            if (jvmFeatures.serialization.useJson) {
                implementation(catalogDep(ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_JSON_MODULE))
            }
        }
    }
}

private fun KotlinDependencyHandler.configureTestDependencies() {
    with(project) {
        implementation(catalogDep(ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_MODULE))
        if (jvmFeatures.coroutines) {
            implementation(catalogDep(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_TEST_MODULE))
        }
        if (jvmFeatures.extendedGradle) {
            implementation(catalogDep(COM_JAVIERSC_GRADLE_GRADLE_TEST_EXTENSIONS_MODULE))
        }
        if (jvmFeatures.extendedTesting) {
            implementation(catalogDep(IO_KOTEST_KOTEST_ASSERTIONS_CORE_MODULE))
        }
    }
}
