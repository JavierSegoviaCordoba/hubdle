@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-android-library`: PluginDependencySpec
    get() = javiersc("android.library")

fun PluginDependenciesSpec.`javiersc-android-library`(version: String): PluginDependencySpec =
    javiersc("android.library", version)
