@file:Suppress("UnusedReceiverParameter")

import com.javiersc.hubdle.extensions._internal.PluginIds
import org.gradle.kotlin.dsl.the
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

public val KotlinDependencyHandler.compose: ComposePlugin.Dependencies
    get() {
        project.pluginManager.apply(PluginIds.Kotlin.compose)
        return project.the()
    }
