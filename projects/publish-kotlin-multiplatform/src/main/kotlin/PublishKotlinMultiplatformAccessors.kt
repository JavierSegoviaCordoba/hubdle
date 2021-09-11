@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-publish-kotlin-multiplatform`: PluginDependencySpec
    get() = javiersc("publish.kotlin.multiplatform")

fun PluginDependenciesSpec.`javiersc-publish-kotlin-multiplatform`(
    version: String,
): PluginDependencySpec = javiersc("publish.kotlin.multiplatform", version)
