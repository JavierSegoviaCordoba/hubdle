@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-build-version-catalogs-settings`: PluginDependencySpec
    get() = javiersc("build.version.catalogs.settings")

fun PluginDependenciesSpec.`javiersc-build-version-catalogs-settings`(version: String): PluginDependencySpec =
    javiersc("build.version.catalogs.settings", version)
