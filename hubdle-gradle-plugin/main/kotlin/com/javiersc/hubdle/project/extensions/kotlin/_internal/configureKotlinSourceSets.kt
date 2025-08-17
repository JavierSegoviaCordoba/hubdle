package com.javiersc.hubdle.project.extensions.kotlin._internal

import com.android.build.api.dsl.AndroidSourceDirectorySet
import com.android.build.api.dsl.AndroidSourceSet
import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.javiersc.gradle.properties.extensions.setProperty
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.COMMON_MAIN
import com.javiersc.hubdle.project.extensions._internal.MAIN
import com.javiersc.hubdle.project.extensions._internal.TEST_FIXTURES
import com.javiersc.hubdle.project.extensions._internal.TEST_FUNCTIONAL
import com.javiersc.hubdle.project.extensions._internal.TEST_FUNCTIONAL_IMPLEMENTATION
import com.javiersc.hubdle.project.extensions._internal.TEST_INTEGRATION
import com.javiersc.hubdle.project.extensions._internal.TEST_INTEGRATION_IMPLEMENTATION
import com.javiersc.hubdle.project.extensions.android._internal.findAndroidCommonExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleSourceSetConfigurableExtension as HubdleSrcSetConfExt
import com.javiersc.hubdle.project.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.hubdle.project.extensions.shared.PluginId
import com.javiersc.hubdle.project.tasks.lifecycle.TestsTask
import com.javiersc.kotlin.stdlib.decapitalize
import java.io.File
import org.gradle.api.Project
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.provider.Provider
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.register
import org.gradle.language.base.plugins.LifecycleBasePlugin.CHECK_TASK_NAME
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinSingleTargetExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget

internal fun HubdleSrcSetConfExt<*>.configurableTestFixtures() {
    applicablePlugin(
        isEnabled = isTestFixturesFullEnabled,
        scope = Scope.CurrentProject,
        pluginId = PluginId.JavaTestFixtures,
    )
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
                mustRunAfter(tasks.named { it == "test" })
            }

        tasks.named(CHECK_TASK_NAME).dependsOn(integrationTestTask)

        tasks.named(TestsTask.NAME).dependsOn(integrationTestTask)

        project.dependencies {
            add(TEST_INTEGRATION_IMPLEMENTATION, project)
            if (isTestFixturesFullEnabled.get()) {
                add(TEST_INTEGRATION_IMPLEMENTATION, project.dependencies.testFixtures(project))
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
                mustRunAfter(tasks.named { it == "test" })
            }

        tasks.named(CHECK_TASK_NAME).dependsOn(functionalTestTask)

        tasks.named(TestsTask.NAME).dependsOn(functionalTestTask)

        project.dependencies {
            add(TEST_FUNCTIONAL_IMPLEMENTATION, project)
            if (isTestFixturesFullEnabled.get()) {
                add(TEST_FUNCTIONAL_IMPLEMENTATION, project.dependencies.testFixtures(project))
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
    fun AndroidSourceDirectorySet.setSrc(name: Provider<String>, dirName: String) {
        setSrcDirs(emptySet<File>())
        srcDirs(project.normalAndGeneratedDirs(name.map { "$it/$dirName" }))
    }
    fun SourceDirectorySet.setSrc(name: Provider<String>, dirName: String) {
        setSrcDirs(emptySet<File>())
        srcDirs(project.normalAndGeneratedDirs(name.map { "$it/$dirName" }))
    }

    project.findAndroidCommonExtension()?.sourceSets?.configureEach { set: AndroidSourceSet ->
        val name: Provider<String> = project.calculateKmpSourceSetDirectory(set.name, targets)
        val manifestPath: Provider<String> = name.map { name -> "$name/AndroidManifest.xml" }
        if (!hubdleKotlinMultiplatform.isFullEnabled.get()) {
            set.assets.setSrc(name, "assets")
            set.java.setSrc(name, "java")
            set.kotlin.setSrc(name, "kotlin")
            set.res.setSrc(name, "res")
            set.resources.setSrc(name, "resources")
            set.manifest.srcFile(manifestPath)
        } else {
            set.manifest.srcFile(manifestPath)
        }
    }

    project.extensions.findByType<JavaPluginExtension>()?.sourceSets?.configureEach { set ->
        val name: Provider<String> = project.calculateKmpSourceSetDirectory(set.name, targets)
        set.java.setSrc(name, "java")
        set.kotlin?.setSrc(name, "kotlin")
        set.resources.setSrc(name, "resources")
    }

    project.extensions.findByType<KotlinProjectExtension>()?.sourceSets?.configureEach { set ->
        val name: Provider<String> = project.calculateKmpSourceSetDirectory(set.name, targets)
        set.kotlin.setSrc(name, "kotlin")
        set.resources.setSrc(name, "resources")
    }
}

internal fun Project.normalAndGeneratedDirs(dir: Provider<String>): SetProperty<File> =
    setProperty {
        setOf(
            projectDir.resolve(dir.get()),
            layout.buildDirectory.asFile.get().resolve("generated").resolve(dir.get()),
        )
    }

private fun Project.calculateKmpSourceSetDirectory(
    name: String,
    targetsProp: SetProperty<String>,
): Provider<String> =
    project.provider {
        val targets: Set<String> = targetsProp.get().toSet()
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
        directory
    }

private val SourceSet.kotlin: SourceDirectorySet?
    get() = (this as ExtensionAware).extensions.findByName("kotlin") as? SourceDirectorySet

private fun KotlinTarget.configureAdditionalTestCompilations(name: String) {
    val additionalTestCompilation = compilation(name)
    val testFixturesCompilation = testFixturesCompilation
    val mainCompilation = mainCompilation

    if (additionalTestCompilation != null) {
        mainCompilation?.let(additionalTestCompilation::associateWith)
        testFixturesCompilation?.let(additionalTestCompilation::associateWith)
    }
}

private val KotlinTarget.mainCompilation: KotlinCompilation<*>?
    get() = compilation(COMMON_MAIN) ?: compilation(MAIN)

private val KotlinTarget.testFixturesCompilation: KotlinCompilation<*>?
    get() = compilation(TEST_FIXTURES)

private fun KotlinTarget.compilation(name: String): KotlinCompilation<*>? =
    compilations.findByName(name)

private val KotlinProjectExtension.targets: Iterable<KotlinTarget>
    get() =
        when (this) {
            is KotlinSingleTargetExtension<*> -> listOf(this.target)
            is KotlinMultiplatformExtension -> targets
            else -> error("Unexpected 'kotlin' extension $this")
        }
