@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-versioning`: PluginDependencySpec
    get() = javiersc("versioning")

fun PluginDependenciesSpec.`javiersc-versioning`(
    version: String,
): PluginDependencySpec = javiersc("versioning", version)
