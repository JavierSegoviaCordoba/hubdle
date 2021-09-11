@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-kotlin-jvm`: PluginDependencySpec
    get() = javiersc("kotlin.jvm")

fun PluginDependenciesSpec.`javiersc-kotlin-jvm`(
    version: String,
): PluginDependencySpec = javiersc("kotlin.jvm", version)
