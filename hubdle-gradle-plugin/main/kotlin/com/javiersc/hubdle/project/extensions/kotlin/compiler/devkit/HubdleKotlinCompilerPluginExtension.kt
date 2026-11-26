package com.javiersc.hubdle.project.extensions.kotlin.compiler.devkit

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.shared.PluginId
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.jetbrains.kotlin.compiler.plugin.devkit.DevKitCompilerConfigExtension

/**
 * A Kotlin compiler plugin, built with the DevKit `compiler-plugin` plugin: it is compiled and
 * tested against every registered Kotlin compiler version, and published as one multi-release jar
 * that embeds its dependencies.
 */
@HubdleDslMarker
public open class HubdleKotlinCompilerPluginExtension @Inject constructor(project: Project) :
    HubdleKotlinCompilerDevKitExtension(project) {

    override val devKitPluginId: PluginId =
        PluginId.JetbrainsKotlinCompilerPluginDevKitCompilerPlugin

    /** The fully qualified name of the `DevKitComponentRegistrar` implementation. */
    public val componentRegistrar: Property<String> = propertyOptional()

    @HubdleDslMarker
    public fun componentRegistrar(value: String) {
        componentRegistrar.set(value)
    }

    /** The fully qualified name of the `DevKitCLP` implementation. */
    public val commandLineProcessor: Property<String> = propertyOptional()

    @HubdleDslMarker
    public fun commandLineProcessor(value: String) {
        commandLineProcessor.set(value)
    }

    override fun configureDevKit(devKit: DevKitCompilerConfigExtension) {
        super.configureDevKit(devKit)
        componentRegistrar.orNull?.let { devKit.componentRegistrar.set(it) }
        commandLineProcessor.orNull?.let { devKit.commandLineProcessor.set(it) }
    }
}

internal val HubdleEnableableExtension.hubdleKotlinCompilerPlugin:
    HubdleKotlinCompilerPluginExtension
    get() = getHubdleExtension()

internal val Project.hubdleKotlinCompilerPlugin: HubdleKotlinCompilerPluginExtension
    get() = getHubdleExtension()
