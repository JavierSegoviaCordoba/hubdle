package com.javiersc.hubdle.extensions.kotlin._internal

import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.extensions._internal.COMMON_MAIN
import com.javiersc.hubdle.extensions._internal.COMMON_TEST
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.MAIN
import com.javiersc.hubdle.extensions._internal.PluginId
import com.javiersc.hubdle.extensions._internal.TEST
import com.javiersc.hubdle.extensions._internal.TEST_FIXTURES
import com.javiersc.hubdle.extensions._internal.TEST_FUNCTIONAL
import com.javiersc.hubdle.extensions._internal.TEST_INTEGRATION
import com.javiersc.hubdle.extensions.android._internal.findAndroidCommonExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleSourceSetConfigurableExtension as HubdleSrcSetConfExt
import com.javiersc.hubdle.extensions.config.testing.ALL_TEST_TASK_NAME
import com.javiersc.hubdle.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.kotlin.stdlib.decapitalize
import org.gradle.api.Task
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
import org.gradle.kotlin.dsl.named
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
        priority = priority,
        scope = Scope.CurrentProject,
        pluginId = PluginId.JavaTestFixtures
    )
    configurable(isEnabled = isTestFixturesFullEnabled) {
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

internal fun HubdleSrcSetConfExt<*>.configurableTestIntegrationSourceSets() {
    configurable(isEnabled = isTestFunctionalFullEnabled) {
        val testIntegrationSourceSet = the<SourceSetContainer>().maybeCreate(TEST_INTEGRATION)

        val integrationTestTask: TaskProvider<Test> =
            tasks.register<Test>("integrationTest") {
                testClassesDirs = testIntegrationSourceSet.output.classesDirs
                classpath = testIntegrationSourceSet.runtimeClasspath
                mustRunAfter(tasks.findByName("test"))
            }

        tasks.named<Task>(CHECK_TASK_NAME) { dependsOn(integrationTestTask) }

        tasks.namedLazily<Task>(ALL_TEST_TASK_NAME).configureEach {
            it.dependsOn(integrationTestTask)
        }

        project.dependencies {
            "testIntegrationImplementation"(project)
            if (isTestFixturesFullEnabled.get()) {
                "testIntegrationImplementation"(project.dependencies.testFixtures(project))
            }
        }
    }
}

internal fun HubdleSrcSetConfExt<KotlinSourceSet>.configurableKotlinTestIntegrationSourceSets() {
    configurable(isEnabled = isTestIntegrationEnabled) {
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
    configurable(isEnabled = isTestFunctionalFullEnabled) {
        val testFunctionalSourceSet = the<SourceSetContainer>().maybeCreate(TEST_FUNCTIONAL)

        val functionalTestTask: TaskProvider<Test> =
            tasks.register<Test>("functionalTest") {
                testClassesDirs = testFunctionalSourceSet.output.classesDirs
                classpath = testFunctionalSourceSet.runtimeClasspath
                mustRunAfter(tasks.findByName("test"))
            }

        tasks.named<Task>(CHECK_TASK_NAME) { dependsOn(functionalTestTask) }

        tasks.namedLazily<Task>(ALL_TEST_TASK_NAME).configureEach {
            it.dependsOn(functionalTestTask)
        }

        project.dependencies {
            "testFunctionalImplementation"(project)
            if (isTestFixturesFullEnabled.get()) {
                "testFunctionalImplementation"(project.dependencies.testFixtures(project))
            }
        }
    }
}

internal fun HubdleSrcSetConfExt<KotlinSourceSet>.configurableKotlinTestFunctionalSourceSets() {
    configurable(isEnabled = isTestFunctionalFullEnabled, priority = Priority.P6) {
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
) {
    configurable {
        project.findAndroidCommonExtension()?.sourceSets?.configureEach { set ->
            val name = set.name.calculateKmpSourceSetDirectory(targets.get())
            if (!hubdleKotlinMultiplatform.isFullEnabled.get()) {
                set.assets.setSrcDirs(listOf("$name/assets"))
                set.java.setSrcDirs(listOf("$name/java"))
                set.kotlin.setSrcDirs(listOf("$name/kotlin"))
                set.manifest.srcFile("$name/manifest")
                set.res.setSrcDirs(listOf("$name/res"))
                set.resources.setSrcDirs(listOf("$name/resources"))
            } else {
                set.manifest.srcFile("$name/manifest")
            }
        }

        project.extensions.findByType<JavaPluginExtension>()?.sourceSets?.configureEach { set ->
            val name = set.name.calculateKmpSourceSetDirectory(targets.get())
            set.java.setSrcDirs(listOf("$name/java"))
            set.kotlin.setSrcDirs(listOf("$name/kotlin"))
            set.resources.setSrcDirs(listOf("$name/resources"))
        }

        project.extensions.findByType<KotlinProjectExtension>()?.sourceSets?.configureEach { set ->
            val name = set.name.calculateKmpSourceSetDirectory(targets.get())
            set.kotlin.setSrcDirs(listOf("$name/kotlin"))
            set.resources.setSrcDirs(listOf("$name/resources"))
        }
    }
}

private fun String.calculateKmpSourceSetDirectory(targets: Set<String>): String {
    val name = this
    val target: String? =
        targets
            .filter { target -> name.startsWith(target) }
            .maxByOrNull { target -> target.count() }

    val directory =
        when {
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

private val KotlinTarget.testCompilation: KotlinCompilation<KotlinCommonOptions>?
    get() = compilation(COMMON_TEST) ?: compilation(TEST)

private val KotlinTarget.testFixturesCompilation: KotlinCompilation<KotlinCommonOptions>?
    get() = compilation(TEST_FIXTURES)

private fun KotlinTarget.compilation(name: String): KotlinCompilation<*>? =
    compilations.findByName(name)
