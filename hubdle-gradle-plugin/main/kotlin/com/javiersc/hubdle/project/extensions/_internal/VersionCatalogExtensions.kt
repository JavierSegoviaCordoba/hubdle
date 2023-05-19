package com.javiersc.hubdle.project.extensions._internal

import com.javiersc.gradle.logging.extensions.infoColored
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import kotlin.jvm.optionals.getOrNull
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.findByType

internal fun HubdleEnableableExtension.libraryVersion(name: String): String? =
    project.hubdleCatalog?.findLibrary(name)?.get()?.get()?.version

internal fun HubdleEnableableExtension.library(
    name: String
): Provider<MinimalExternalModuleDependency?> = provider {
    project.hubdleCatalog?.findLibrary(name)?.get()?.orNull
}

internal fun Project.library(name: String): Provider<MinimalExternalModuleDependency?> = provider {
    hubdleCatalog?.findLibrary(name)?.getOrNull()?.orNull
}

internal val Project.hubdleCatalog: VersionCatalog?
    get() {
        val versionCatalogExtension: VersionCatalogsExtension? = extensions.findByType()
        if (versionCatalogExtension == null) {
            logger.infoColored { "Hubdle Settings plugin must be applied" }
        }
        return versionCatalogExtension?.named("hubdle")
    }
