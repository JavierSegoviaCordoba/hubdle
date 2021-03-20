@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-publish-gradle-plugin`: PluginDependencySpec
    get() = javiersc("publish.gradle.plugin")

fun PluginDependenciesSpec.`javiersc-publish-gradle-plugin`(version: String): PluginDependencySpec =
    javiersc("publish.gradle.plugin", version)
