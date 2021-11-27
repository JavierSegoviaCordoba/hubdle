@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-readme-badges-generator`: PluginDependencySpec
    get() = javiersc("readme.badges.generator")

fun PluginDependenciesSpec.`javiersc-readme-badges-generator`(
    version: String,
): PluginDependencySpec = javiersc("readme.badges.generator", version)
