@file:Suppress("FunctionName", "TopLevelPropertyNaming", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.`javiersc-kotlin-multiplatform-no-android`: PluginDependencySpec
    get() = javiersc("kotlin.multiplatform.no.android")

fun PluginDependenciesSpec.`javiersc-kotlin-multiplatform-no-android`(
    version: String,
): PluginDependencySpec = javiersc("kotlin.multiplatform.no.android", version)
