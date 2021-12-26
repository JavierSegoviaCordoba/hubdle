@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-code-coverage`: PluginDependencySpec
    get() = javiersc("code.coverage")

fun PluginDependenciesSpec.`javiersc-code-coverage`(
    version: String,
): PluginDependencySpec = javiersc("code.coverage", version)
