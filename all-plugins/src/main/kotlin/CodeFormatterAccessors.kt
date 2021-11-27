@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-code-formatter`: PluginDependencySpec
    get() = javiersc("code.formatter")

fun PluginDependenciesSpec.`javiersc-code-formatter`(
    version: String,
): PluginDependencySpec = javiersc("code.formatter", version)
