package com.javiersc.hubdle.extensions.kotlin._internal

import com.javiersc.hubdle.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.extensions._internal.PluginId
import com.javiersc.hubdle.extensions._internal.sourceSetOutput
import com.javiersc.hubdle.extensions.android._internal.findAndroidCommonExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleSourceSetConfigurableExtension as HubdleSrcSetConfExt
import com.javiersc.hubdle.extensions.kotlin.multiplatform.hubdleKotlinMultiplatform
import com.javiersc.kotlin.stdlib.decapitalize
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

internal fun HubdleSrcSetConfExt<*>.configureTestFixtures() {
    applicablePlugin(
        isEnabled = isTestFixturesFullEnabled,
        priority = priority,
        scope = Scope.CurrentProject,
        pluginId = PluginId.JavaTestFixtures
    )
}

internal fun HubdleSrcSetConfExt<*>.configureTestIntegrationSourceSets() {
    configurable(isEnabled = isTestIntegrationEnabled) {
        configure<SourceSetContainer> { register("testIntegration") }
        project.dependencies.configure<DependencyHandlerScope> {
            "testIntegrationImplementation"(project)
            if (isTestFixturesFullEnabled.get()) {
                "testIntegrationImplementation"(sourceSetOutput("textFixtures"))
            }
        }
    }
}

internal fun HubdleSrcSetConfExt<KotlinSourceSet>.configureKotlinTestIntegrationSourceSets() {
    configurable(isEnabled = isTestIntegrationEnabled) {
        configure<KotlinProjectExtension> { sourceSets.register("testIntegration") }
        testIntegration.configure {
            it.dependencies {
                implementation(project)
                if (isTestFixturesFullEnabled.get()) {
                    implementation(sourceSetOutput("textFixtures"))
                }
            }
        }
    }
}

internal fun HubdleSrcSetConfExt<*>.configureTestFunctionalSourceSets() {
    configurable(isEnabled = isTestFunctionalFullEnabled) {
        configure<SourceSetContainer> { register("testFunctional") }
        project.dependencies.configure<DependencyHandlerScope> {
            "testFunctionalImplementation"(project)
            if (isTestFixturesFullEnabled.get()) {
                "testFunctionalImplementation"(sourceSetOutput("textFixtures"))
            }
        }
    }
}

internal fun HubdleSrcSetConfExt<KotlinSourceSet>.configureKotlinTestFunctionalSourceSets() {
    configurable(isEnabled = isTestFunctionalFullEnabled) {
        configure<KotlinProjectExtension> { sourceSets.register("testFunctional") }
        testFunctional.configure {
            it.dependencies {
                implementation(project)
                if (isTestFixturesFullEnabled.get()) {
                    implementation(sourceSetOutput("textFixtures"))
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
