@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-changelog`: PluginDependencySpec
    get() = javiersc("changelog")

fun PluginDependenciesSpec.`javiersc-changelog`(version: String): PluginDependencySpec =
    javiersc("changelog", version)
