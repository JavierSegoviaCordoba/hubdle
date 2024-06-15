package com.javiersc.hubdle.project.extensions.kotlin._internal

import com.android.build.api.dsl.AndroidSourceSet
import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.COMMON_MAIN
import com.javiersc.hubdle.project.extensions._internal.MAIN
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.TEST_FIXTURES
import com.javiersc.hubdle.project.extensions._internal.TEST_FUNCTIONAL
import com.javiersc.hubdle.project.extensions._internal.TEST_INTEGRATION
import com.javiersc.hubdle.project.extensions.android._internal.findAndroidCommonExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleSourceSetConfigurableExtension as HubdleSrcSetConfExt
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.tasks.lifecycle.TestsTask
import com.javiersc.kotlin.stdlib.decapitalize
import java.io.File
import org.gradle.api.Project
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.register
import org.gradle.language.base.plugins.LifecycleBasePlugin.CHECK_TASK_NAME
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.targets

internal fun HubdleSrcSetConfExt<*>.configurableTestFixtures() {
    applicablePlugin(
        isEnabled = isTestFixturesFullEnabled,
        scope = Scope.CurrentProject,
        pluginId = PluginId.JavaTestFixtures)
    lazyConfigurable(isEnabled = isTestFixturesFullEnabled) {
        configure<KotlinProjectExtension> {
            targets.map { target ->
                val testFixturesCompilation = target.testFixturesCompilation
                val mainCompilation = target.mainCompilation
                if (testFixturesCompilation != null && mainCompilation != null) {
                    testFixturesCompilation.associateWith(mainCompilation)
                }
            }
        }
    }
}

internal fun HubdleSrcSetConfExt<*>.configurableTestIntegrationSourceSets() =
    lazyConfigurable(isEnabled = isTestFunctionalFullEnabled) {
        val testIntegrationSourceSet = the<SourceSetContainer>().maybeCreate(TEST_INTEGRATION)

        val integrationTestTask: TaskProvider<Test> =
            tasks.register<Test>("integrationTest") {
                testClassesDirs = testIntegrationSourceSet.output.classesDirs
                classpath = testIntegrationSourceSet.runtimeClasspath
                mustRunAfter(tasks.findByName("test"))
            }

        tasks.named(CHECK_TASK_NAME).dependsOn(integrationTestTask)

        tasks.named(TestsTask.NAME).dependsOn(integrationTestTask)

        project.dependencies {
            "testIntegrationImplementation"(project)
            if (isTestFixturesFullEnabled.get()) {
                "testIntegrationImplementation"(project.dependencies.testFixtures(project))
            }
        }
    }

internal fun HubdleSrcSetConfExt<KotlinSourceSet>.configurableKotlinTestIntegrationSourceSets() {
    lazyConfigurable(isEnabled = isTestIntegrationEnabled) {
        configure<KotlinProjectExtension> {
            sourceSets.maybeCreate(TEST_INTEGRATION)
            targets.forEach { target ->
                target.configureAdditionalTestCompilations(TEST_INTEGRATION)
            }
        }
        testIntegration.configure {
            it.dependencies {
                implementation(project)
                if (isTestFixturesFullEnabled.get()) {
                    implementation(project.dependencies.testFixtures(project))
                }
            }
        }
    }
}

internal fun HubdleSrcSetConfExt<*>.configurableTestFunctionalSourceSets() {
    lazyConfigurable(isEnabled = isTestFunctionalFullEnabled) {
        val testFunctionalSourceSet = the<SourceSetContainer>().maybeCreate(TEST_FUNCTIONAL)

        val functionalTestTask: TaskProvider<Test> =
            tasks.register<Test>("functionalTest") {
                testClassesDirs = testFunctionalSourceSet.output.classesDirs
                classpath = testFunctionalSourceSet.runtimeClasspath
                mustRunAfter(tasks.findByName("test"))
            }

        tasks.named(CHECK_TASK_NAME).dependsOn(functionalTestTask)

        tasks.named(TestsTask.NAME).dependsOn(functionalTestTask)

        project.dependencies {
            "testFunctionalImplementation"(project)
            if (isTestFixturesFullEnabled.get()) {
                "testFunctionalImplementation"(project.dependencies.testFixtures(project))
            }
        }
    }
}

internal fun HubdleSrcSetConfExt<KotlinSourceSet>.configurableKotlinTestFunctionalSourceSets() {
    lazyConfigurable(isEnabled = isTestFunctionalFullEnabled) {
        configure<KotlinProjectExtension> {
            sourceSets.maybeCreate(TEST_FUNCTIONAL)
            targets.forEach { target ->
                target.configureAdditionalTestCompilations(TEST_FUNCTIONAL)
            }
        }
        testFunctional.configure {
            it.dependencies {
                implementation(project)
                if (isTestFixturesFullEnabled.get()) {
                    implementation(project.dependencies.testFixtures(project))
                }
            }
        }
    }
}

internal fun HubdleConfigurableExtension.configurableSrcDirs(
    targets: SetProperty<String> = setProperty { emptySet() }
) = beforeConfigurable {
    project.findAndroidCommonExtension()?.sourceSets?.configureEach { set: AndroidSourceSet ->
        val name: String = set.name.calculateKmpSourceSetDirectory(targets.get())
        if (!hubdleKotlinMultiplatform.isFullEnabled.get()) {
            set.assets.setSrcDirs(project.normalAndGeneratedDirs("$name/assets"))
            set.java.setSrcDirs(project.normalAndGeneratedDirs("$name/java"))
            set.kotlin.setSrcDirs(project.normalAndGeneratedDirs("$name/kotlin"))
            set.manifest.srcFile("$name/AndroidManifest.xml")
            set.res.setSrcDirs(project.normalAndGeneratedDirs("$name/res"))
            set.resources.setSrcDirs(project.normalAndGeneratedDirs("$name/resources"))
        } else {
            set.manifest.srcFile("$name/AndroidManifest.xml")
        }
    }

    project.extensions.findByType<JavaPluginExtension>()?.sourceSets?.configureEach { set ->
        val name: String = set.name.calculateKmpSourceSetDirectory(targets.get())
        set.java.setSrcDirs(project.normalAndGeneratedDirs("$name/java"))
        set.kotlin.setSrcDirs(project.normalAndGeneratedDirs("$name/kotlin"))
        set.resources.setSrcDirs(project.normalAndGeneratedDirs("$name/resources"))
    }

    project.extensions.findByType<KotlinProjectExtension>()?.sourceSets?.configureEach { set ->
        val name: String = set.name.calculateKmpSourceSetDirectory(targets.get())
        set.kotlin.setSrcDirs(project.normalAndGeneratedDirs("$name/kotlin"))
        set.resources.setSrcDirs(project.normalAndGeneratedDirs("$name/resources"))
    }
}

internal fun Project.normalAndGeneratedDirs(dir: String): List<File> =
    listOf(
        projectDir.resolve(dir),
        layout.buildDirectory.asFile.get().resolve("generated").resolve(dir),
    )

private fun String.calculateKmpSourceSetDirectory(targets: Set<String>): String {
    val name: String = this
    val target: String? =
        targets
            .filter { target -> name.startsWith(target) }
            .maxByOrNull { target -> target.count() }

    val directory: String =
        when {
            name.startsWith("androidNative") && target == "android" -> {
                val type = name.substringAfter("androidNative").decapitalize()
                "androidNative/$type"
            }
            target != null -> {
                val type = name.substringAfter(target).decapitalize()
                "$target/$type"
            }
            else -> name
        }
    return directory
}

private val SourceSet.kotlin: SourceDirectorySet
    get() = (this as ExtensionAware).extensions.getByName("kotlin") as SourceDirectorySet

private fun KotlinTarget.configureAdditionalTestCompilations(name: String) {
    val additionalTestCompilation = compilation(name)
    val testFixturesCompilation = testFixturesCompilation
    val mainCompilation = mainCompilation

    if (additionalTestCompilation != null) {
        mainCompilation?.let(additionalTestCompilation::associateWith)
        testFixturesCompilation?.let(additionalTestCompilation::associateWith)
    }
}

private val KotlinTarget.mainCompilation: KotlinCompilation<KotlinCommonOptions>?
    get() = compilation(COMMON_MAIN) ?: compilation(MAIN)

private val KotlinTarget.testFixturesCompilation: KotlinCompilation<KotlinCommonOptions>?
    get() = compilation(TEST_FIXTURES)

private fun KotlinTarget.compilation(name: String): KotlinCompilation<*>? =
    compilations.findByName(name)
