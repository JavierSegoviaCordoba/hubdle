package com.javiersc.hubdle.project.extensions.kotlin.android

import com.javiersc.gradle.properties.extensions.getBooleanProperty
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.kotlin.android.application.HubdleKotlinAndroidApplicationExtension
import com.javiersc.hubdle.project.extensions.kotlin.android.application.hubdleAndroidApplication
import com.javiersc.hubdle.project.extensions.kotlin.android.features.HubdleKotlinAndroidFeaturesExtension
import com.javiersc.hubdle.project.extensions.kotlin.android.library.HubdleKotlinAndroidLibraryExtension
import com.javiersc.hubdle.project.extensions.kotlin.android.library.hubdleAndroidLibrary
import com.javiersc.hubdle.project.extensions.kotlin.hubdleKotlin
import com.javiersc.kotlin.stdlib.remove
import java.io.File
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

// TODO: transform into `HubdleConfigurableExtension`
@HubdleDslMarker
public open class HubdleKotlinAndroidExtension @Inject constructor(project: Project) :
    HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlin)

    public val namespace: Property<String?> = property { project.calculateAndroidNamespace() }

    public val minSdk: Property<Int> = property { 23 }

    public val compileSdk: Property<Int> = property { 35 }

    public val targetSdk: Property<Int> = property { 35 }

    public val features: HubdleKotlinAndroidFeaturesExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun features(action: Action<HubdleKotlinAndroidFeaturesExtension> = Action {}) {
        features.enableAndExecute(action)
    }

    public val application: HubdleKotlinAndroidApplicationExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun application(action: Action<HubdleKotlinAndroidApplicationExtension> = Action {}) {
        application.enableAndExecute(action)
    }

    public val library: HubdleKotlinAndroidLibraryExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun library(action: Action<HubdleKotlinAndroidLibraryExtension> = Action {}) {
        library.enableAndExecute(action)
    }

    public object Android {
        public const val namespace: String = "android.namespace"
        public const val namespaceUseProject: String = "android.namespace.use.project"
        public const val namespaceUseKotlinFile: String = "android.namespace.use.kotlinFile"
    }

    private fun Project.calculateAndroidNamespace(): String? =
        when {
            getBooleanProperty(Android.namespaceUseProject).orNull == true -> {
                calculateAndroidNamespaceWithProject()
            }
            getBooleanProperty(Android.namespaceUseKotlinFile).orNull == true -> {
                calculateAndroidNamespaceWithKotlinFile()
            }
            else -> calculateAndroidNamespaceWithProject()
        }

    private fun Project.calculateAndroidNamespaceWithProject(): String {
        val sanitizedProjectGroup = group.toString().sanitize()
        val sanitizedProjectName = name.sanitize()
        val splitByDot = "$sanitizedProjectGroup.$sanitizedProjectName".split(".")
        return splitByDot.reduce { acc, current ->
            if (acc.split(".").lastOrNull() == current) acc else "$acc.$current"
        }
    }

    private fun Project.calculateAndroidNamespaceWithKotlinFile(): String? =
        extensions
            .findByType<KotlinProjectExtension>()
            ?.sourceSets
            ?.asMap
            ?.values
            ?.asSequence()
            ?.flatMap { it.kotlin.sourceDirectories }
            ?.firstOrNull { it.path.endsWith("main${File.separator}kotlin") }
            ?.walkTopDown()
            ?.onEnter { parentFile ->
                parentFile.listFiles().orEmpty().firstOrNull { it.extension == "kt" } == null
            }
            ?.flatMap { it.listFiles().orEmpty().toList() }
            ?.firstOrNull { it.listFiles().orEmpty().any { it.extension == "kt" } }
            ?.listFiles()
            ?.firstOrNull { it.extension == "kt" }
            ?.readLines()
            ?.firstOrNull { it.startsWith("package ") }
            ?.remove("package ")

    private fun String.sanitize(): String =
        replace(Regex("""\d+"""), "")
            .replace(File.separator, ".")
            .replace("--", ".")
            .replace("-", ".")
            .replace("__", ".")
            .replace("_", ".")
            .replace("..", ".")
            .remove(" ")
            .dropLastWhile { !it.isLetter() }
}

internal val HubdleEnableableExtension.hubdleAndroid: HubdleKotlinAndroidExtension
    get() = getHubdleExtension()

internal val Project.hubdleAndroid: HubdleKotlinAndroidExtension
    get() = getHubdleExtension()

internal val HubdleEnableableExtension.hubdleAndroidAny: Set<HubdleConfigurableExtension>
    get() = setOf(hubdleAndroidApplication, hubdleAndroidLibrary)

internal val Project.hubdleAndroidAny: Set<HubdleConfigurableExtension>
    get() = setOf(hubdleAndroidApplication, hubdleAndroidLibrary)
