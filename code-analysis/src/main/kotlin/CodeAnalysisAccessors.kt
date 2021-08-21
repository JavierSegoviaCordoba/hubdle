@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-code-analysis`: PluginDependencySpec
    get() = javiersc("code.analysis")

fun PluginDependenciesSpec.`javiersc-code-analysis`(
    version: String,
): PluginDependencySpec = javiersc("code.analysis", version)
