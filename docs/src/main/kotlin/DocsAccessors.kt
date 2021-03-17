@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-docs`: PluginDependencySpec
    get() = javiersc("docs")

fun PluginDependenciesSpec.`javiersc-docs`(version: String): PluginDependencySpec =
    javiersc("docs", version)
