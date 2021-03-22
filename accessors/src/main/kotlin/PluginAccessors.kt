@file:Suppress("TopLevelPropertyNaming")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

private const val JavierSC = "com.javiersc.gradle.plugins"

fun PluginDependenciesSpec.javiersc(name: String, version: String? = null): PluginDependencySpec =
    if (version.isNullOrBlank()) id("$JavierSC.$name") else id("$JavierSC.$name").version(version)
