@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-gradle-wrapper-updater`: PluginDependencySpec
    get() = javiersc("gradle.wrapper.updater")

fun PluginDependenciesSpec.`javiersc-gradle-wrapper-updater`(
    version: String,
): PluginDependencySpec = javiersc("gradle.wrapper.updater", version)
