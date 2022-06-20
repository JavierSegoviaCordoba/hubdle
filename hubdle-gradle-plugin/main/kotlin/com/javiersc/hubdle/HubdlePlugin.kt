package com.javiersc.hubdle

import com.javiersc.gradle.properties.extensions.getProperty
import com.javiersc.hubdle.extensions.HubdleExtension
import com.javiersc.hubdle.extensions._internal.state.hubdleCatalogsDependencies
import com.javiersc.hubdle.extensions._internal.state.userCatalogsDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create

public abstract class HubdlePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.group = target.getProperty(HubdleProperty.Project.group)
        target.pluginManager.apply(BasePlugin::class)

        hubdleCatalogsDependencies.configure(target)
        userCatalogsDependencies.configure(target)

        target.extensions.create<HubdleExtension>("InternalHubdle")
    }
}
