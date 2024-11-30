package hubdle.declarative.platform

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.AppliedPlugin

public enum class PluginIds(public val id: String) {
    JetBrainsKotlinAndroid("org.jetbrains.kotlin.android"),
    JetBrainsKotlinJvm("org.jetbrains.kotlin.jvm"),
    JetBrainsKotlinMultiplatform("org.jetbrains.kotlin.multiplatform"),
}

public fun Project.withPlugin(pluginId: PluginIds, action: Action<AppliedPlugin>): Unit =
    pluginManager.withPlugin(pluginId.id, action)

public fun Project.withJetBrainsKotlinAndroidPlugin(action: Action<AppliedPlugin>): Unit =
    withPlugin(PluginIds.JetBrainsKotlinAndroid, action)

public fun Project.withJetBrainsKotlinJvmPlugin(action: Action<AppliedPlugin>): Unit =
    withPlugin(PluginIds.JetBrainsKotlinJvm, action)

public fun Project.withJetBrainsKotlinMultiplatformPlugin(action: Action<AppliedPlugin>): Unit =
    withPlugin(PluginIds.JetBrainsKotlinMultiplatform, action)

public fun Project.withJetBrainsKotlinPlugin(action: Action<AppliedPlugin>) {
    withJetBrainsKotlinAndroidPlugin(action)
    withJetBrainsKotlinJvmPlugin(action)
    withJetBrainsKotlinMultiplatformPlugin(action)
}
