package com.javiersc.hubdle

import com.javiersc.hubdle.project.HubdleProjectPlugin
import com.javiersc.hubdle.settings.HubdleSettingsPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import org.gradle.api.plugins.PluginAware
import org.gradle.kotlin.dsl.apply

public abstract class HubdlePlugin : Plugin<PluginAware> {

    override fun apply(target: PluginAware) {
        when (target) {
            is Project -> target.pluginManager.apply(HubdleProjectPlugin::class)
            is Settings -> target.pluginManager.apply(HubdleSettingsPlugin::class)
            else -> error("Hubdle cannot be applied to ${target::class}")
        }
    }
}
