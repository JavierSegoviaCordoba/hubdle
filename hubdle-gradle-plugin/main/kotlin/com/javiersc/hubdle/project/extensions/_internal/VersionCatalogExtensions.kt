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

internal fun HubdleEnableableExtension.libraryVersion(alias: String): Provider<String> =
    library(alias).map { it.version }

internal fun HubdleEnableableExtension.library(
    alias: String
): Provider<MinimalExternalModuleDependency> = project.library(alias)

internal fun Project.libraryPlatform(alias: String): String =
    provider {
            val bom = hubdleCatalog?.findLibrary(alias)?.getOrNull()?.orNull
            checkNotNull(bom) { "bom dependency not found" }
            "${bom.module}:${bom.version}"
        }
        .get()

internal fun Project.library(alias: String): Provider<MinimalExternalModuleDependency> = provider {
    hubdleCatalog?.findLibrary(alias)?.getOrNull()?.orNull
}

internal fun Project.libraryModule(alias: String): Provider<String> = provider {
    hubdleCatalog?.findLibrary(alias)?.getOrNull()?.orNull?.module?.toString()
}

internal val Project.hubdleCatalog: VersionCatalog?
    get() {
        val versionCatalogExtension: VersionCatalogsExtension? = extensions.findByType()
        if (versionCatalogExtension == null) {
            logger.infoColored { "Hubdle Settings plugin must be applied" }
        }
        return versionCatalogExtension?.named("hubdle")
    }
