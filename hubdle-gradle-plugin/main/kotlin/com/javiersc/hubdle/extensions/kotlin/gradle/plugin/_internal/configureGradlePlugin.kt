package com.javiersc.hubdle.extensions.kotlin.gradle.plugin._internal

import com.gradle.publish.PluginBundleExtension
import com.javiersc.gradle.properties.extensions.getProperty
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
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
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_JUNIT5_MODULE
import com.javiersc.hubdle.extensions.dependencies._internal.constants.ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_MODULE
import com.javiersc.hubdle.extensions.kotlin._internal.configJvmTarget
import com.javiersc.hubdle.extensions.options.configureDefaultJavaSourceSets
import com.javiersc.hubdle.extensions.options.configureDefaultKotlinSourceSets
import com.javiersc.hubdle.extensions.options.configureJavaJarsForPublishing
import com.javiersc.hubdle.extensions.options.configureMavenPublication
import com.javiersc.hubdle.extensions.options.configurePublishingExtension
import com.javiersc.hubdle.extensions.options.configureSigningForPublishing
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.Configuration
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.gradleKotlinDsl
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.gradle.plugin.devel.tasks.PluginUnderTestMetadata
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import testFixtures

internal fun configureGradlePlugin(project: Project) {
    if (project.hubdleState.kotlin.gradle.plugin.isEnabled) {
        project.pluginManager.apply(PluginIds.Gradle.javaGradlePlugin)
        project.pluginManager.apply(PluginIds.Gradle.javaTestFixtures)
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

internal fun Project.configureGradlePluginTestSourceSets() {
    the<SourceSetContainer>().maybeCreate("functionalTest")
    the<KotlinProjectExtension>().sourceSets.maybeCreate("functionalTest")
    the<SourceSetContainer>().maybeCreate("integrationTest")
    the<KotlinProjectExtension>().sourceSets.maybeCreate("integrationTest")
}

internal fun Project.configureGradlePluginTestTasks() {
    val functionalTest = the<SourceSetContainer>().named("functionalTest")
    val integrationTest = the<SourceSetContainer>().named("integrationTest")

    val integrationTestTask =
        tasks.maybeRegisterLazily<Test>("integrationTest") { task ->
            task.description = "Runs the integration tests."
            task.group = "verification"

            task.testClassesDirs = functionalTest.get().output.classesDirs
            task.classpath = functionalTest.get().runtimeClasspath
            task.mustRunAfter(tasks.namedLazily<Task>("test"))
        }

    val functionalTestTask =
        tasks.maybeRegisterLazily<Test>("functionalTest") { task ->
            task.description = "Runs the functional tests."
            task.group = "verification"

            task.testClassesDirs = integrationTest.get().output.classesDirs
            task.classpath = integrationTest.get().runtimeClasspath
            task.mustRunAfter(tasks.namedLazily<Task>("test"))
        }

    tasks.namedLazily<Task>("allTests") { task ->
        task.dependsOn(functionalTestTask)
        task.dependsOn(integrationTestTask)
    }

    tasks.namedLazily<Task>("check") { task ->
        task.dependsOn(functionalTestTask)
        task.dependsOn(integrationTestTask)
    }
}

private fun Project.configurePluginUnderTestDependencies() {
    val pluginUnderTestDependencies =
        hubdleState.kotlin.gradle.plugin.pluginUnderTestDependencies.toList()
    if (pluginUnderTestDependencies.isNotEmpty()) {
        val testPluginClasspath: Configuration = configurations.create("testPluginClasspath")

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
    sourceSets.named("functionalTest") { it.dependencies { configureTestDependencies(true) } }
    sourceSets.named("integrationTest") { it.dependencies { configureTestDependencies(true) } }
    sourceSets.named("main") { it.dependencies { configureMainDependencies() } }
    sourceSets.named("test") { it.dependencies { configureTestDependencies(true) } }
    sourceSets.named("testFixtures") { it.dependencies { configureTestDependencies() } }
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

private fun KotlinDependencyHandler.configureTestDependencies(enableFixtures: Boolean = false) {
    implementation(project)
    if (enableFixtures) implementation(testFixtures(project))

    with(project) {
        implementation(catalogDep(ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_MODULE))
        implementation(catalogDep(ORG_JETBRAINS_KOTLIN_KOTLIN_TEST_JUNIT5_MODULE))
        implementation(project.dependencies.gradleTestKit())
        if (gradlePluginFeatures.extendedGradle) {
            implementation(catalogDep(COM_JAVIERSC_GRADLE_GRADLE_TEST_EXTENSIONS_MODULE))
        }

        if (gradlePluginFeatures.extendedTesting) {
            implementation(catalogDep(IO_KOTEST_KOTEST_ASSERTIONS_CORE_MODULE))
        }
    }
}
