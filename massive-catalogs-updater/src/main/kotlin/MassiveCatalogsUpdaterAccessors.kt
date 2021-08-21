@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-massive-catalogs-updater`: PluginDependencySpec
    get() = javiersc("massive.catalogs.updater")

fun PluginDependenciesSpec.`javiersc-massive-catalogs-updater`(
    version: String,
): PluginDependencySpec = javiersc("massive.catalogs.updater", version)
