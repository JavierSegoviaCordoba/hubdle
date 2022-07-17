@file:Suppress("UnusedReceiverParameter")

import org.jetbrains.compose.ComposePlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

public val KotlinDependencyHandler.compose: ComposePlugin.Dependencies
    get() = ComposePlugin.Dependencies
