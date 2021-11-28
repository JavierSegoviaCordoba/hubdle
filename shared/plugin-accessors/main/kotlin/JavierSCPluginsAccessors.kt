@file:Suppress(
    "FunctionName",
    "TopLevelPropertyNaming",
    "ObjectPropertyName",
    "RemoveRedundantBackticks",
    "TooManyFunctions"
)

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

fun PluginDependenciesSpec.javiersc(name: String, version: String? = null): PluginDependencySpec =
    if (version.isNullOrBlank()) id("com.javiersc.gradle.plugins.$name")
    else id("com.javiersc.gradle.plugins.$name").version(version)
