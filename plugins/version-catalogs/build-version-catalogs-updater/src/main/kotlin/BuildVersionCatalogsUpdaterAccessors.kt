@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-build-version-catalogs-updater`: PluginDependencySpec
    get() = javiersc("build.version.catalogs.updater")

fun PluginDependenciesSpec.`javiersc-build-version-catalogs-updater`(
    version: String,
): PluginDependencySpec = javiersc("build.version.catalogs.updater", version)
