package com.kotlin.devkit

import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

public class ExampleGradlePlugin : KotlinCompilerPluginSupportPlugin {

    override fun applyToCompilation(
        kotlinCompilation: KotlinCompilation<*>
    ): Provider<List<SubpluginOption>> = kotlinCompilation.target.project.provider { emptyList() }

    override fun getCompilerPluginId(): String = PluginInfo.PLUGIN_ID

    override fun getPluginArtifact(): SubpluginArtifact = PluginInfo.PLUGIN_ARTIFACT

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean = true
}
