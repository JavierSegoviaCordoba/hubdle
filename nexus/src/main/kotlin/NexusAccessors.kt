@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-nexus`: PluginDependencySpec
    get() = javiersc("nexus")

fun PluginDependenciesSpec.`javiersc-nexus`(
    version: String,
): PluginDependencySpec = javiersc("nexus", version)
