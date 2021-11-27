@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-dependency-updates`: PluginDependencySpec
    get() = javiersc("dependency.updates")

fun PluginDependenciesSpec.`javiersc-dependency-updates`(
    version: String,
): PluginDependencySpec = javiersc("dependency.updates", version)
