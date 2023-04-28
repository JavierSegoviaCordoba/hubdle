@file:Suppress("MagicNumber")

package com.javiersc.hubdle.project.extensions._internal

import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create

internal val hubdleStateCache: MutableMap<Project, HubdleState> = mutableMapOf()

internal val Project.hubdleState: HubdleState
    get() {
        if (hubdleStateCache[this] == null) hubdleStateCache[this] = HubdleState(this)
        return checkNotNull(hubdleStateCache[this]) {
            "HubdleState for `$displayName` doesn't exist"
        }
    }

internal class HubdleState(private val project: Project) {

    val name: String = project.displayName

    private val _extensions: MutableSet<BaseHubdleExtension> = mutableSetOf()
    val extensions: Set<HubdleConfigurableExtension>
        get() = _extensions.filterIsInstance<HubdleConfigurableExtension>().toSet()

    internal inline fun <reified T : BaseHubdleExtension> createExtension(
        vararg constructionArguments: Any,
        block: T.() -> Unit = {}
    ): T {
        val extensionName = "_Internal${T::class.simpleName}".substringBeforeLast("Extension")
        val extension: T = project.extensions.create(extensionName, *constructionArguments)
        block(extension)
        _extensions.add(extension)
        return extension
    }

    fun configureExtensions() {
        for (extension in extensions) {
            extension.run { project.defaultConfiguration() }
        }
    }

    private val _applicablePlugins: MutableList<ApplicablePlugin> = mutableListOf()
    val applicablePlugins: List<ApplicablePlugin>
        get() = _applicablePlugins.toList().sortedBy { it.priority }

    fun applicablePlugin(
        isEnabled: Property<Boolean>,
        priority: Priority,
        scope: Scope,
        pluginId: PluginId,
    ) {
        val applicablePlugin =
            ApplicablePlugin(
                priority = priority,
                isEnabled = isEnabled,
                scope = scope,
                pluginId = pluginId
            )
        _applicablePlugins.add(applicablePlugin)
    }

    private val _configurables: MutableList<Configurable> = mutableListOf()
    val configurables: List<Configurable>
        get() = _configurables.toList().sortedBy { it.priority }

    fun configurable(
        name: String,
        isEnabled: Property<Boolean>,
        priority: Priority,
        config: Configurable.() -> Unit
    ) {
        _configurables.add(Configurable(name, priority, isEnabled, config))
    }

    internal fun userConfigurable(
        name: String,
        isEnabled: Property<Boolean>,
        config: Configurable.() -> Unit
    ) {
        configurable(name, isEnabled, Priority.P5, config)
    }

    fun configure() {
        for (applicablePlugin in applicablePlugins) {
            applicablePlugin.run { project.applyPlugin() }
        }
        for (configurable in configurables) {
            configurable.configure()
        }
    }
}

internal interface ApplicablePlugin {
    val isEnabled: Property<Boolean>
    val priority: Priority
    val scope: Scope
    val pluginId: PluginId

    fun Project.applyPlugin()

    override fun toString(): String

    enum class Scope {
        AllProjects,
        SubProjects,
        CurrentProject,
    }

    companion object {

        operator fun invoke(
            priority: Priority,
            isEnabled: Property<Boolean>,
            scope: Scope,
            pluginId: PluginId,
        ): ApplicablePlugin =
            object : ApplicablePlugin {
                override val isEnabled: Property<Boolean> = isEnabled
                override val priority: Priority = priority
                override val scope: Scope = scope
                override val pluginId: PluginId = pluginId

                override fun Project.applyPlugin() {
                    if (!isEnabled.get()) return
                    when (scope) {
                        Scope.AllProjects -> allprojects { it.pluginManager.apply(pluginId.id) }
                        Scope.SubProjects -> subprojects { it.pluginManager.apply(pluginId.id) }
                        Scope.CurrentProject -> pluginManager.apply(pluginId.id)
                    }
                }

                override fun toString(): String =
                    "ApplicablePlugin(pluginId=$pluginId, isEnabled=${isEnabled.get()}, priority=$priority, scope=$scope)"
            }
    }
}

internal interface Configurable {

    val name: String
        get() = "Unknown"

    val priority: Priority

    val isEnabled: Property<Boolean>

    fun configure()

    /** Configuration order is defined with this enum. Lower `value: Int` is higher priority. */
    sealed class Priority(private val value: Int) : Comparable<Priority> {

        /** Configuration that can affect to all configurations like versioning plugins. */
        object P1 : Priority(1) {
            override fun toString(): String = "P1"
        }

        /** Important configurations that affect to other configurations but not all of them. */
        object P2 : Priority(2) {
            override fun toString(): String = "P2"
        }

        /** Default configurations */
        object P3 : Priority(3) {
            override fun toString(): String = "P3"
        }

        /** Less important configurations like secondary ones that are not Kotlin or Android */
        object P4 : Priority(4) {
            override fun toString(): String = "P4"
        }

        /** User configurations */
        object P5 : Priority(5) {
            override fun toString(): String = "P5"
        }

        /**
         * Default configurations which depends on some user ones in third-party plugin
         * configurations that are not lazy
         */
        object P6 : Priority(6) {
            override fun toString(): String = "P6"
        }

        override fun compareTo(other: Priority): Int =
            when {
                value > other.value -> 1
                value == other.value -> 0
                else -> -1
            }
    }

    companion object {

        operator fun invoke(
            name: String,
            priority: Priority,
            isEnabled: Property<Boolean>,
            config: Configurable.() -> Unit,
        ): Configurable =
            object : Configurable {
                override val name: String = name
                override val isEnabled: Property<Boolean> = isEnabled
                override val priority: Priority = priority

                override fun configure() {
                    if (isEnabled.get()) config()
                }
            }
    }
}
