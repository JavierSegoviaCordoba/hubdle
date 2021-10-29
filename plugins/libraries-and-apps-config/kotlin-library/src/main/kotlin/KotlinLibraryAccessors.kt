@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-kotlin-library`: PluginDependencySpec
    get() = javiersc("kotlin.library")

fun PluginDependenciesSpec.`javiersc-kotlin-library`(
    version: String,
): PluginDependencySpec = javiersc("kotlin.library", version)
