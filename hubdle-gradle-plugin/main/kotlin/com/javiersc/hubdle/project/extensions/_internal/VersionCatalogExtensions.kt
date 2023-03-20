package com.javiersc.hubdle.project.extensions._internal

import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.findByType

internal fun HubdleEnableableExtension.libraryVersion(name: String) =
    project.hubdleVersionCatalog.findLibrary(name).get().get().version

internal fun HubdleEnableableExtension.library(name: String) =
    project.hubdleVersionCatalog.findLibrary(name).get()

internal fun Project.library(name: String) = hubdleVersionCatalog.findLibrary(name).get()

internal fun Project.libraryModule(name: String) = library(name).map { it.module.toString() }

private val Project.hubdleVersionCatalog
    get() =
        checkNotNull(extensions.findByType<VersionCatalogsExtension>()) {
                "Hubdle Settings plugin must be applied"
            }
            .named("hubdle")
