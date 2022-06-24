package com.javiersc.hubdle.extensions.kotlin.gradle.version.catalog

import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.PublishingOptions
import java.io.File
import org.gradle.api.Project

public abstract class GradleVersionCatalogExtension : PublishingOptions {

    override fun Project.publishing() {
        pluginManager.apply(PluginIds.Publishing.mavenPublish)
        pluginManager.apply(PluginIds.Publishing.signing)

        hubdleState.kotlin.isPublishingEnabled = true
    }

    public fun Project.files(files: List<File>) {
        hubdleState.kotlin.gradle.versionCatalog.files += files
    }
}
