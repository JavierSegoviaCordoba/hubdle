@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-kotlin-multiplatform`: PluginDependencySpec
    get() = javiersc("kotlin.multiplatform")

fun PluginDependenciesSpec.`javiersc-kotlin-multiplatform`(version: String): PluginDependencySpec =
    javiersc("kotlin.multiplatform", version)
