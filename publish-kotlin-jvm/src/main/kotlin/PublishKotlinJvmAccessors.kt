@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-publish-kotlin-jvm`: PluginDependencySpec
    get() = javiersc("publish.kotlin.jvm")

fun PluginDependenciesSpec.`javiersc-publish-kotlin-jvm`(
    version: String,
): PluginDependencySpec = javiersc("publish.kotlin.jvm", version)
