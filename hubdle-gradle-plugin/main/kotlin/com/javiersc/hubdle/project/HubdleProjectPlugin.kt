package com.javiersc.hubdle.project

import com.javiersc.hubdle.project.extensions.HubdleExtension
import com.javiersc.hubdle.project.extensions._internal.hubdleState
import com.javiersc.hubdle.project.extensions.config._internal.createHubdleConfigExtensions
import com.javiersc.hubdle.project.extensions.java._internal.createHubdleJavaExtensions
import com.javiersc.hubdle.project.extensions.kotlin._internal.createHubdleKotlinExtensions
import com.javiersc.hubdle.project.extensions.shared._internal.createHubdleSharedExtensions
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.kotlin.dsl.apply

public abstract class HubdleProjectPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply(BasePlugin::class)

        target.configureHubdleExtensions()
    }
}

private fun Project.configureHubdleExtensions() {
    hubdleState.createExtension<HubdleExtension>()

    hubdleState.createHubdleConfigExtensions()
    hubdleState.createHubdleJavaExtensions()
    hubdleState.createHubdleKotlinExtensions()
    hubdleState.createHubdleSharedExtensions()

    hubdleState.configureExtensions()
}
