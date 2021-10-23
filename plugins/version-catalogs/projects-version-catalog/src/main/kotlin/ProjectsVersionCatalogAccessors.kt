@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-projects-version-catalog`: PluginDependencySpec
    get() = javiersc("projects.version.catalog")

fun PluginDependenciesSpec.`javiersc-projects-version-catalog`(
    version: String,
): PluginDependencySpec = javiersc("projects.version.catalog", version)
