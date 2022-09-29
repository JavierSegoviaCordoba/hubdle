@file:Suppress("SpreadOperator")

package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal

import com.android.build.api.dsl.LibraryExtension
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.HubdleState
import com.javiersc.hubdle.extensions._internal.state.catalogDependency as catalogDep
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.explicit.api._internal.configureExplicitApi
import com.javiersc.hubdle.extensions.dependencies._internal.constants.COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.COM_JAVIERSC_KOTLIN_KOTLIN_TEST_JUNIT5_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.COM_JAVIERSC_KOTLIN_KOTLIN_TEST_JUNIT_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.COM_JAVIERSC_KOTLIN_KOTLIN_TEST_TESTNG_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.IO_KOTEST_KOTEST_ASSERTIONS_CORE_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_ANDROID_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_CORE_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_TEST_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_CORE_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_JSON_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_MODULE
import com.javiersc.hubdle.extensions.kotlin._internal.configJvmTarget
import com.javiersc.hubdle.extensions.options.configureDefaultJavaSourceSets
import com.javiersc.hubdle.extensions.options.configureDefaultKotlinSourceSets
import com.javiersc.hubdle.extensions.options.configureEmptyJavadocs
import com.javiersc.hubdle.extensions.options.configurePublishingExtension
import com.javiersc.hubdle.extensions.options.configureSigningForPublishing
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.Dependency
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.junit.JUnitOptions
import org.gradle.api.tasks.testing.junitplatform.JUnitPlatformOptions
import org.gradle.api.tasks.testing.testng.TestNGOptions
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

internal fun configureMultiplatform(project: Project) {
    if (project.hubdleState.kotlin.multiplatform.isEnabled) {
        project.pluginManager.apply(PluginIds.Kotlin.multiplatform)

        project.configureExplicitApi()
        project.configJvmTarget()
        project.the<JavaPluginExtension>().configureDefaultJavaSourceSets()
        project.the<KotlinProjectExtension>().configureDefaultKotlinSourceSets()
        project.the<KotlinMultiplatformExtension>().configureMultiplatformDependencies()
        configurePublishing(project)
    }
}

internal fun configureMultiplatformRawConfig(project: Project) {
    project.hubdleState.kotlin.multiplatform.rawConfig.kotlin?.execute(project.the())
}

internal fun configureMultiplatformAndroidRawConfig(project: Project) {
    project.hubdleState.kotlin.multiplatform.android.rawConfig.android?.execute(project.the())
}

internal fun configureSerialization(project: Project) {
    if (project.multiplatformFeatures.serialization.isEnabled) {
        project.pluginManager.apply(PluginIds.Kotlin.serialization)
    }
}

internal fun configureMultiplatformCompose(project: Project) {
    if (project.multiplatformFeatures.compose.isEnabled) {
        project.pluginManager.apply(PluginIds.Kotlin.compose)
        project.extensions.findByType<LibraryExtension>()?.buildFeatures?.compose = true
        project.multiplatformFeatures.compose.desktop?.execute(project.the())
    }
}

private fun KotlinMultiplatformExtension.configureMultiplatformDependencies() {
    sourceSets.getByName("commonMain") { set ->
        set.dependencies { configureCommonMainDependencies() }
    }
    sourceSets.getByName("commonTest") { set ->
        set.dependencies { configureCommonTestDependencies() }
    }
    sourceSets.findByName("androidMain")?.dependencies { configureAndroidMainDependencies() }
}

internal val Project.multiplatformFeatures: HubdleState.Kotlin.Multiplatform.Features
    get() = hubdleState.kotlin.multiplatform.features

private val KotlinDependencyHandler.multiplatformFeatures: HubdleState.Kotlin.Multiplatform.Features
    get() = project.multiplatformFeatures

private fun configurePublishing(project: Project) {
    if (project.hubdleState.config.publishing.isEnabled) {
        project.pluginManager.apply(PluginIds.Publishing.mavenPublish)
        project.configurePublishingExtension()
        project.configureSigningForPublishing()
        project.configureEmptyJavadocs()
    }
}

private fun KotlinDependencyHandler.configureCommonMainDependencies() {
    if (multiplatformFeatures.coroutines) {
        implementation(catalogDep(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_CORE_MODULE))
    }
    if (multiplatformFeatures.extendedStdlib) {
        implementation(catalogDep(COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE))
    }
    if (multiplatformFeatures.serialization.isEnabled) {
        implementation(catalogDep(ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_CORE_MODULE))
        if (multiplatformFeatures.serialization.useJson) {
            implementation(catalogDep(ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_JSON_MODULE))
        }
    }
}

private fun KotlinDependencyHandler.configureCommonTestDependencies() {
    if (multiplatformFeatures.coroutines) {
        implementation(catalogDep(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_TEST_MODULE))
    }
    implementation(catalogDep(ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_MODULE))

    if (multiplatformFeatures.extendedTesting) {
        project.commonTestImplementationConfiguration?.withDependencies { set ->
            set.addLater(calculateJavierScKotlinTestDependency())
        }
        implementation(catalogDep(IO_KOTEST_KOTEST_ASSERTIONS_CORE_MODULE))
    }
}

private val Project.commonTestImplementationConfiguration: Configuration?
    get() = project.configurations.findByName("commonTestImplementation")

private fun KotlinDependencyHandler.calculateJavierScKotlinTestDependency(): Provider<Dependency> {
    return project.provider {
        when (project.tasks.withType<Test>().firstOrNull()?.options) {
            is JUnitOptions -> catalogDep(COM_JAVIERSC_KOTLIN_KOTLIN_TEST_JUNIT_MODULE)
            is JUnitPlatformOptions -> catalogDep(COM_JAVIERSC_KOTLIN_KOTLIN_TEST_JUNIT5_MODULE)
            is TestNGOptions -> catalogDep(COM_JAVIERSC_KOTLIN_KOTLIN_TEST_TESTNG_MODULE)
            else -> catalogDep(COM_JAVIERSC_KOTLIN_KOTLIN_TEST_JUNIT_MODULE)
        }.run(::implementation)
    }
}

private fun KotlinDependencyHandler.configureAndroidMainDependencies() {
    if (multiplatformFeatures.coroutines) {
        implementation(catalogDep(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_ANDROID_MODULE))
    }
}
