@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-publish-version-catalog`: PluginDependencySpec
    get() = javiersc("publish.version.catalog")

fun PluginDependenciesSpec.`javiersc-publish-version-catalog`(
    version: String,
): PluginDependencySpec = javiersc("publish.version.catalog", version)
