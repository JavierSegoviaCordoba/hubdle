@file:Suppress("SpreadOperator")

package com.javiersc.hubdle.extensions.kotlin.multiplatform._internal

import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.HubdleState
import com.javiersc.hubdle.extensions._internal.state.catalogImplementation
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.explicit.api._internal.configureExplicitApi
import com.javiersc.hubdle.extensions.dependencies._internal.constants.COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE
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
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.the
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

        if (project.hubdleState.config.publishing.isEnabled) {
            project.pluginManager.apply(PluginIds.Publishing.mavenPublish)
            project.configurePublishingExtension()
            project.configureSigningForPublishing()
            project.configureEmptyJavadocs()
        }
    }
}

internal fun configureMultiplatformRawConfig(project: Project) {
    project.hubdleState.kotlin.multiplatform.rawConfig.kotlin?.execute(project.the())
}

internal fun configureMultiplatformAndroidRawConfig(project: Project) {
    project.hubdleState.kotlin.multiplatform.android.rawConfig.android?.execute(project.the())
}

private fun KotlinMultiplatformExtension.configureMultiplatformDependencies() {
    sourceSets.getByName("commonMain") { dependencies { configureCommonMainDependencies() } }
    sourceSets.getByName("commonTest") { dependencies { configureCommonTestDependencies() } }
    sourceSets.findByName("androidMain")?.apply {
        dependencies { configureAndroidMainDependencies() }
    }
}

internal val Project.multiplatformFeatures: HubdleState.Kotlin.Multiplatform.Features
    get() = hubdleState.kotlin.multiplatform.features

private val KotlinDependencyHandler.multiplatformFeatures: HubdleState.Kotlin.Multiplatform.Features
    get() = project.multiplatformFeatures

private fun KotlinDependencyHandler.configureCommonMainDependencies() {
    if (multiplatformFeatures.coroutines) {
        catalogImplementation(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_CORE_MODULE)
    }
    if (multiplatformFeatures.extendedStdlib) {
        catalogImplementation(COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE)
    }

    if (multiplatformFeatures.serialization.isEnabled) {
        project.pluginManager.apply(PluginIds.Kotlin.serialization)
        catalogImplementation(ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_CORE_MODULE)
        if (multiplatformFeatures.serialization.useJson) {
            catalogImplementation(ORG_JETBRAINS_KOTLINX_KOTLINX_SERIALIZATION_JSON_MODULE)
        }
    }
}

private fun KotlinDependencyHandler.configureCommonTestDependencies() {
    if (multiplatformFeatures.coroutines) {
        catalogImplementation(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_TEST_MODULE)
    }
    catalogImplementation(ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_MODULE)
    if (multiplatformFeatures.extendedTesting) {
        catalogImplementation(IO_KOTEST_KOTEST_ASSERTIONS_CORE_MODULE)
    }
}

private fun KotlinDependencyHandler.configureAndroidMainDependencies() {
    if (multiplatformFeatures.coroutines) {
        catalogImplementation(ORG_JETBRAINS_KOTLINX_KOTLINX_COROUTINES_ANDROID_MODULE)
    }
}
