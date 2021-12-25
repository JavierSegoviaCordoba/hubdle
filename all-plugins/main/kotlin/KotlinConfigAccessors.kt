@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-kotlin-config`: PluginDependencySpec
    get() = javiersc("kotlin.config")

fun PluginDependenciesSpec.`javiersc-kotlin-config`(
    version: String,
): PluginDependencySpec = javiersc("kotlin.config", version)
