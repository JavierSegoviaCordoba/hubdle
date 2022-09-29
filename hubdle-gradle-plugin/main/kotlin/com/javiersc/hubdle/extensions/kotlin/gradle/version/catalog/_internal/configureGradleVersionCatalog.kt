package com.javiersc.hubdle.extensions.kotlin.gradle.version.catalog._internal

import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.configureMavenPublication
import com.javiersc.hubdle.extensions.options.configurePublishingExtension
import com.javiersc.hubdle.extensions.options.configureSigningForPublishing
import org.gradle.api.Project
import org.gradle.api.plugins.catalog.CatalogPluginExtension
import org.gradle.kotlin.dsl.configure

internal fun configureGradleVersionCatalog(project: Project) {
    if (project.hubdleState.kotlin.gradle.versionCatalog.isEnabled) {
        project.pluginManager.apply(PluginIds.Gradle.versionCatalog)

        project.configure<CatalogPluginExtension> {
            versionCatalog { catalog ->
                catalog.from(project.hubdleState.kotlin.gradle.versionCatalog.catalogs)
            }
        }

        if (project.hubdleState.config.publishing.isEnabled) {
            project.pluginManager.apply(PluginIds.Publishing.mavenPublish)
            project.configurePublishingExtension()
            project.configureMavenPublication("versionCatalog")
            project.configureSigningForPublishing()
        }
    }
}
