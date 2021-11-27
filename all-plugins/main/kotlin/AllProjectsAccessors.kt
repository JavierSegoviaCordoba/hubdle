@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-all-projects`: PluginDependencySpec
    get() = javiersc("all.projects")

fun PluginDependenciesSpec.`javiersc-all-projects`(
    version: String,
): PluginDependencySpec = javiersc("all.projects", version)
