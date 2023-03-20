package com.javiersc.hubdle.project.extensions.apis

import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin
import com.javiersc.hubdle.project.extensions._internal.Configurable
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.HubdleState
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.hubdleState
import com.javiersc.hubdle.project.extensions._internal.listProperty
import com.javiersc.hubdle.project.extensions._internal.property
import com.javiersc.hubdle.project.extensions._internal.setProperty
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.TaskContainer
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the

public abstract class HubdleEnableableExtension(
    internal open val project: Project,
) : BaseHubdleEnableableExtension {

    internal val tasks: TaskContainer
        get() = project.tasks

    internal val name: String =
        this::class.simpleName?.substringBeforeLast("_Decorated") ?: "UnknownHubdleExtension"

    internal open val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = emptySet()

    internal open val noneOfExtensions: Set<HubdleEnableableExtension>
        get() = emptySet()

    internal open val requiredExtensions: Set<HubdleEnableableExtension>
        get() = emptySet()

    internal val hubdleState: HubdleState
        get() = project.hubdleState

    internal val isFullEnabled: Property<Boolean> = property {
        isEnabled.get() && oneOfCondition.get() && requiredCondition.get() && noneCondition.get()
    }

    private val oneOfCondition: Property<Boolean>
        get() = property {
            (oneOfExtensions.any { it.isFullEnabled.get() } || oneOfExtensions.isEmpty())
        }

    private val requiredCondition: Property<Boolean>
        get() = property {
            (requiredExtensions.all { it.isFullEnabled.get() || requiredExtensions.isEmpty() })
        }

    private val noneCondition: Property<Boolean>
        get() = property {
            noneOfExtensions.isEmpty() || noneOfExtensions.none { it.isFullEnabled.get() }
        }

    internal fun applicablePlugin(
        isEnabled: Property<Boolean>,
        priority: Priority,
        scope: ApplicablePlugin.Scope,
        pluginId: PluginId,
    ) {
        hubdleState.applicablePlugin(isEnabled, priority, scope, pluginId)
    }

    internal fun applicablePlugin(
        priority: Priority,
        scope: ApplicablePlugin.Scope,
        pluginId: PluginId,
    ) {
        applicablePlugin(isFullEnabled, priority, scope, pluginId)
    }

    internal fun configurable(
        isEnabled: Property<Boolean>,
        priority: Priority,
        config: Configurable.() -> Unit
    ) {
        hubdleState.configurable(name, isEnabled, priority, config)
    }

    internal fun configurable(priority: Priority, config: Configurable.() -> Unit) {
        configurable(isFullEnabled, priority, config)
    }

    internal fun userConfigurable(config: Configurable.() -> Unit) {
        hubdleState.userConfigurable(name, isFullEnabled, config)
    }

    internal inline fun <reified T> provider(crossinline block: Project.() -> T): Provider<T> =
        project.provider { block(project) }

    internal inline fun <reified T> property(crossinline block: Project.() -> T): Property<T> =
        project.property { block(this) }

    internal inline fun <reified T> listProperty(
        crossinline block: Project.() -> List<T>
    ): ListProperty<T> = project.listProperty { block(this) }

    internal inline fun <reified T> setProperty(
        crossinline block: Project.() -> Set<T>
    ): SetProperty<T> = project.setProperty { block(this) }

    internal inline fun <reified T> the(): T = project.the()

    internal inline fun <reified T : Any> configure(crossinline block: T.() -> Unit): Unit =
        project.configure<T> { block(this) }
}

internal fun <T : HubdleEnableableExtension> T.enableAndExecute(action: Action<T>) {
    enabled(true)
    action.execute(this)
}
