package com.javiersc.hubdle

import com.javiersc.gradle.plugin.extensions.Plugin
import com.javiersc.gradle.properties.extensions.getProperty
import com.javiersc.hubdle.extensions.HubdleExtension
import com.javiersc.hubdle.extensions._internal.hubdleCatalogsDependencies
import com.javiersc.hubdle.extensions._internal.hubdleState
import com.javiersc.hubdle.extensions._internal.userCatalogsDependencies
import com.javiersc.hubdle.extensions.config._internal.createHubdleConfigExtensions
import com.javiersc.hubdle.extensions.kotlin._internal.createHubdleKotlinExtensions
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.kotlin.dsl.apply

public abstract class HubdlePlugin : Plugin<Project> {

    override fun Project.apply() {
        group = getProperty(HubdleProperty.Project.group)
        pluginManager.apply(BasePlugin::class)

        configureHubdleExtensions()
        configureDependencies()
    }
}

private fun Project.configureHubdleExtensions() {
    hubdleState.createExtension<HubdleExtension>()

    hubdleState.createHubdleConfigExtensions()
    hubdleState.createHubdleKotlinExtensions()

    hubdleState.configureExtensions()
}

private fun Project.configureDependencies() {
    hubdleCatalogsDependencies.configure()
    userCatalogsDependencies.configure(this)
}
