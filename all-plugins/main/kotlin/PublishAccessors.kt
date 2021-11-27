@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-publish`: PluginDependencySpec
    get() = javiersc("publish")

fun PluginDependenciesSpec.`javiersc-publish`(
    version: String,
): PluginDependencySpec = javiersc("publish", version)
