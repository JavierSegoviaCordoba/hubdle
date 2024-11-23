@file:Suppress("UnstableApiUsage")

package com.javiersc.hubdle.declarative

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import org.gradle.api.plugins.PluginAware
import org.gradle.kotlin.dsl.apply

public abstract class HubdleDeclarativePlugin : Plugin<PluginAware> {

    override fun apply(target: PluginAware) {
        when (target) {
            is Project -> target.pluginManager.apply(HubdleDeclarativeProjectPlugin::class)
            is Settings -> target.pluginManager.apply(HubdleDeclarativeSettingsPlugin::class)
            else -> error("Hubdle Declarative cannot be applied to ${target::class}")
        }
    }
}
