package com.javiersc.hubdle.project

import com.javiersc.gradle.properties.extensions.getProperty
import com.javiersc.hubdle.project.extensions.HubdleExtension
import com.javiersc.hubdle.project.extensions._internal.hubdleCatalogsDependencies
import com.javiersc.hubdle.project.extensions._internal.hubdleState
import com.javiersc.hubdle.project.extensions._internal.userCatalogsDependencies
import com.javiersc.hubdle.project.extensions.config._internal.createHubdleConfigExtensions
import com.javiersc.hubdle.project.extensions.kotlin._internal.createHubdleKotlinExtensions
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.kotlin.dsl.apply

public abstract class HubdleProjectPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.group = target.getProperty(HubdleProperty.Project.group)
        target.pluginManager.apply(BasePlugin::class)

        target.configureHubdleExtensions()
        target.configureDependencies()
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
