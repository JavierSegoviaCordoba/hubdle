package com.javiersc.hubdle.extensions._internal.state.kotlin.gradle

import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.configureMavenPublication
import com.javiersc.hubdle.extensions.configurePublishingExtension
import com.javiersc.hubdle.extensions.configureSigningForPublishing
import org.gradle.api.Project
import org.gradle.api.plugins.catalog.CatalogPluginExtension
import org.gradle.kotlin.dsl.the

internal fun configureGradleVersionCatalog(project: Project) {
    if (hubdleState.kotlin.gradle.versionCatalog.isEnabled) {
        project.pluginManager.apply(PluginIds.Gradle.versionCatalog)

        project.the<CatalogPluginExtension>().apply {
            versionCatalog { it.from(hubdleState.kotlin.gradle.versionCatalog.files) }
        }

        if (hubdleState.kotlin.isPublishingEnabled) {
            project.pluginManager.apply(PluginIds.Publishing.mavenPublish)
            project.pluginManager.apply(PluginIds.Publishing.signing)
            project.configurePublishingExtension()
            project.configureMavenPublication("versionCatalog")
            project.configureSigningForPublishing()
        }
    }
}
