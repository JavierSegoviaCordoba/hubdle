@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-publish-android-library`: PluginDependencySpec
    get() = javiersc("publish.android.library")

fun PluginDependenciesSpec.`javiersc-publish-android-library`(
    version: String,
): PluginDependencySpec = javiersc("publish.android.library", version)
