package com.javiersc.hubdle.project.extensions.kotlin.compiler.devkit

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.shared.PluginId
import javax.inject.Inject
import org.gradle.api.Project

/**
 * A library for Kotlin compiler plugins, built with the DevKit `compiler-library` plugin: it is
 * compiled against every registered Kotlin compiler version, and published as one multi-release
 * jar.
 */
@HubdleDslMarker
public open class HubdleKotlinCompilerLibraryExtension @Inject constructor(project: Project) :
    HubdleKotlinCompilerDevKitExtension(project) {

    override val devKitPluginId: PluginId =
        PluginId.JetbrainsKotlinCompilerPluginDevKitCompilerLibrary
}

internal val HubdleEnableableExtension.hubdleKotlinCompilerLibrary:
    HubdleKotlinCompilerLibraryExtension
    get() = getHubdleExtension()

internal val Project.hubdleKotlinCompilerLibrary: HubdleKotlinCompilerLibraryExtension
    get() = getHubdleExtension()
