@file:Suppress("MagicNumber")

package com.javiersc.hubdle.project.extensions._internal

import com.javiersc.gradle.project.extensions.withPlugins
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable.After
import com.javiersc.hubdle.project.extensions._internal.Configurable.Before
import com.javiersc.hubdle.project.extensions._internal.Configurable.Lazy
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
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
        get() = _applicablePlugins.toList()

    fun applicablePlugin(
        isEnabled: Provider<Boolean>,
        scope: Scope,
        pluginId: PluginId,
    ) {
        val applicablePlugin =
            ApplicablePlugin(isEnabled = isEnabled, scope = scope, pluginId = pluginId)
        _applicablePlugins.add(applicablePlugin)
    }

    private val _configurables: MutableList<Configurable> = mutableListOf()
    val configurables: List<Configurable>
        get() = _configurables.toList()

    fun beforeConfigurable(name: String, isEnabled: Property<Boolean>, config: Before.() -> Unit) {
        _configurables.add(Before(name, isEnabled, config))
    }

    fun lazyConfigurable(name: String, isEnabled: Property<Boolean>, config: Lazy.() -> Unit) {
        _configurables.add(Lazy(name, isEnabled, config))
    }

    fun afterConfigurable(name: String, isEnabled: Property<Boolean>, config: After.() -> Unit) {
        _configurables.add(After(name, isEnabled, config))
    }

    fun configure() {
        for (applicablePlugin in applicablePlugins) {
            applicablePlugin.run { project.applyPlugin() }
        }
        val ids = applicablePlugins.filter { it.isEnabled.get() }.map { it.pluginId.id }
        project.withPlugins(*ids.toTypedArray()) {
            val beforeConfigurables = configurables.filterIsInstance<Before>()
            for (configurable in beforeConfigurables) configurable.configure()

            val lazyConfigurables = configurables.filterIsInstance<Lazy>()
            for (configurable in lazyConfigurables) configurable.configure()

            val afterConfigurables = configurables.filterIsInstance<After>()
            for (configurable in afterConfigurables) configurable.configure()
        }
    }
}

internal interface ApplicablePlugin {
    val isEnabled: Provider<Boolean>
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
            isEnabled: Provider<Boolean>,
            scope: Scope,
            pluginId: PluginId,
        ): ApplicablePlugin =
            object : ApplicablePlugin {
                override val isEnabled: Provider<Boolean> = isEnabled
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
                    "ApplicablePlugin(pluginId=$pluginId, isEnabled=${isEnabled.get()}, scope=$scope)"
            }
    }
}

internal sealed interface Configurable {

    val name: String
        get() = "Unknown"

    val isEnabled: Property<Boolean>

    fun configure()

    data class Before(
        override val name: String,
        override val isEnabled: Property<Boolean>,
        private val config: Before.() -> Unit,
    ) : Configurable {

        override fun configure() {
            if (isEnabled.get()) config()
        }
    }

    data class Lazy(
        override val name: String,
        override val isEnabled: Property<Boolean>,
        private val config: Lazy.() -> Unit,
    ) : Configurable {

        override fun configure() {
            if (isEnabled.get()) config()
        }
    }

    data class After(
        override val name: String,
        override val isEnabled: Property<Boolean>,
        private val config: After.() -> Unit,
    ) : Configurable {

        override fun configure() {
            if (isEnabled.get()) config()
        }
    }
}
