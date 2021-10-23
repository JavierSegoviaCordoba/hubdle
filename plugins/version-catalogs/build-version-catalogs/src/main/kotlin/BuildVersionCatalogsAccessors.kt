@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-build-version-catalogs`: PluginDependencySpec
    get() = javiersc("build.version.catalogs")

fun PluginDependenciesSpec.`javiersc-build-version-catalogs`(
    version: String,
): PluginDependencySpec = javiersc("build.version.catalogs", version)
