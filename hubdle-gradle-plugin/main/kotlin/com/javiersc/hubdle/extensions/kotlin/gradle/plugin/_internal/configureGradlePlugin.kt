package com.javiersc.hubdle.extensions.kotlin.gradle.plugin._internal

import com.gradle.publish.PluginBundleExtension
import com.javiersc.gradle.properties.extensions.getProperty
import com.javiersc.hubdle.HubdleProperty
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.HubdleState
import com.javiersc.hubdle.extensions._internal.state.catalogDependency as catalogDep
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.explicit.api._internal.configureExplicitApi
import com.javiersc.hubdle.extensions.dependencies._internal.constants.COM_JAVIERSC_GRADLE_GRADLE_EXTENSIONS_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.COM_JAVIERSC_GRADLE_GRADLE_TEST_EXTENSIONS_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.IO_KOTEST_KOTEST_ASSERTIONS_CORE_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_MODULE
import com.javiersc.hubdle.extensions.kotlin._internal.configJvmTarget
import com.javiersc.hubdle.extensions.options.configureDefaultJavaSourceSets
import com.javiersc.hubdle.extensions.options.configureDefaultKotlinSourceSets
import com.javiersc.hubdle.extensions.options.configureJavaJarsForPublishing
import com.javiersc.hubdle.extensions.options.configureMavenPublication
import com.javiersc.hubdle.extensions.options.configurePublishingExtension
import com.javiersc.hubdle.extensions.options.configureSigningForPublishing
import com.javiersc.semver.Version
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.attributes.Category.CATEGORY_ATTRIBUTE
import org.gradle.api.attributes.Category.LIBRARY
import org.gradle.api.attributes.Usage.JAVA_RUNTIME
import org.gradle.api.attributes.Usage.USAGE_ATTRIBUTE
import org.gradle.api.attributes.plugin.GradlePluginApiVersion.GRADLE_PLUGIN_API_VERSION_ATTRIBUTE
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.gradleKotlinDsl
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.gradle.plugin.devel.tasks.PluginUnderTestMetadata
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

internal fun configureGradlePlugin(project: Project) {
    if (project.hubdleState.kotlin.gradle.plugin.isEnabled) {
        project.pluginManager.apply(PluginIds.Gradle.javaGradlePlugin)
        project.pluginManager.apply(PluginIds.Kotlin.jvm)

        project.configureExplicitApi()
        project.configJvmTarget()
        project.the<JavaPluginExtension>().configureDefaultJavaSourceSets()
        project.the<KotlinProjectExtension>().configureDefaultKotlinSourceSets()
        project.the<KotlinJvmProjectExtension>().configureGradleDependencies()
        project.hubdleState.kotlin.gradle.plugin.gradlePlugin?.execute(project.the())

        project.configurePluginUnderTestDependencies()

        if (project.hubdleState.config.publishing.isEnabled) {
            project.pluginManager.apply(PluginIds.Publishing.mavenPublish)
            project.pluginManager.apply(PluginIds.Publishing.gradlePluginPublish)
            project.configureJavaJarsForPublishing()
            project.configurePublishingExtension()
            project.configureMavenPublication("java")
            project.configureSigningForPublishing()
            project.configure<PluginBundleExtension> {
                tags = project.hubdleState.kotlin.gradle.plugin.tags
                website = project.getProperty(HubdleProperty.POM.url)
                vcsUrl = project.getProperty(HubdleProperty.POM.scmUrl)
            }
        }
    }
}

private fun Project.configurePluginUnderTestDependencies() {
    val pluginUnderTestDependencies =
        hubdleState.kotlin.gradle.plugin.pluginUnderTestDependencies.toList()
    if (pluginUnderTestDependencies.isNotEmpty()) {
        val testPluginClasspath: Configuration by
            configurations.creating {
                val kotlinVersion = Version.safe(project.getKotlinPluginVersion()).getOrNull()
                if (kotlinVersion != null && kotlinVersion >= Version("1.7.0"))
                    attributes { attributes ->
                        attributes.attribute(USAGE_ATTRIBUTE, objects.named(JAVA_RUNTIME))
                        attributes.attribute(CATEGORY_ATTRIBUTE, objects.named(LIBRARY))
                        attributes.attribute(
                            GRADLE_PLUGIN_API_VERSION_ATTRIBUTE,
                            objects.named("7.0")
                        )
                    }
            }

        dependencies {
            for (dependency in pluginUnderTestDependencies) {
                testPluginClasspath(dependency)
            }
        }

        tasks.withType<PluginUnderTestMetadata>().configureEach { metadata ->
            metadata.pluginClasspath.from(testPluginClasspath)
        }
    }
}

internal fun configureKotlinGradlePluginRawConfig(project: Project) {
    project.hubdleState.kotlin.gradle.plugin.rawConfig.kotlin?.execute(project.the())
    project.hubdleState.kotlin.gradle.plugin.rawConfig.gradlePlugin?.execute(project.the())
}

private fun KotlinJvmProjectExtension.configureGradleDependencies() {
    sourceSets.named("main") { set -> set.dependencies { configureMainDependencies() } }
    sourceSets.named("test") { set -> set.dependencies { configureTestDependencies() } }
}

internal val Project.gradlePluginFeatures: HubdleState.Kotlin.Gradle.Plugin.Features
    get() = hubdleState.kotlin.gradle.plugin.features

private fun KotlinDependencyHandler.configureMainDependencies() {
    with(project) {
        implementation(dependencies.gradleApi())
        implementation(gradleKotlinDsl())

        if (gradlePluginFeatures.extendedGradle) {
            implementation(catalogDep(COM_JAVIERSC_GRADLE_GRADLE_EXTENSIONS_MODULE))
        }
        if (gradlePluginFeatures.extendedGradle) {
            implementation(catalogDep(COM_JAVIERSC_KOTLIN_KOTLIN_STDLIB_MODULE))
        }
    }
}

private fun KotlinDependencyHandler.configureTestDependencies() {
    with(project) {
        implementation(catalogDep(ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_MODULE))
        implementation(project.dependencies.gradleTestKit())
        if (gradlePluginFeatures.extendedGradle) {
            implementation(catalogDep(COM_JAVIERSC_GRADLE_GRADLE_TEST_EXTENSIONS_MODULE))
        }

        if (gradlePluginFeatures.extendedTesting) {
            implementation(catalogDep(IO_KOTEST_KOTEST_ASSERTIONS_CORE_MODULE))
        }
    }
}
