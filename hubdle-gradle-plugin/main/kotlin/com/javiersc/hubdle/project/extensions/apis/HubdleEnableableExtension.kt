package com.javiersc.hubdle.project.extensions.apis

import com.javiersc.gradle.properties.extensions.listProperty
import com.javiersc.gradle.properties.extensions.property
import com.javiersc.gradle.properties.extensions.setProperty
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin
import com.javiersc.hubdle.project.extensions._internal.Configurable.After
import com.javiersc.hubdle.project.extensions._internal.Configurable.Before
import com.javiersc.hubdle.project.extensions._internal.Configurable.Lazy
import com.javiersc.hubdle.project.extensions._internal.HubdleState
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.hubdleState
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
    override val project: Project,
) : BaseHubdleEnableableExtension {

    internal val tasks: TaskContainer
        get() = project.tasks

    internal val extensionName: String =
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
        isEnabled: Provider<Boolean>,
        scope: ApplicablePlugin.Scope,
        pluginId: PluginId,
    ) {
        hubdleState.applicablePlugin(isEnabled, scope, pluginId)
    }

    internal fun applicablePlugin(
        scope: ApplicablePlugin.Scope,
        pluginId: PluginId,
    ) {
        applicablePlugin(isFullEnabled, scope, pluginId)
    }

    internal fun beforeConfigurable(isEnabled: Property<Boolean>, config: Before.() -> Unit) {
        hubdleState.beforeConfigurable(extensionName, isEnabled, config)
    }

    internal fun beforeConfigurable(config: Before.() -> Unit) {
        beforeConfigurable(isFullEnabled, config)
    }

    internal fun lazyConfigurable(isEnabled: Property<Boolean>, config: Lazy.() -> Unit) {
        hubdleState.lazyConfigurable(extensionName, isEnabled, config)
    }

    internal fun lazyConfigurable(config: Lazy.() -> Unit) {
        lazyConfigurable(isFullEnabled, config)
    }

    internal fun afterConfigurable(isEnabled: Property<Boolean>, config: After.() -> Unit) {
        hubdleState.afterConfigurable(extensionName, isEnabled, config)
    }

    internal fun afterConfigurable(config: After.() -> Unit) {
        afterConfigurable(isFullEnabled, config)
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

internal fun <T : HubdleEnableableExtension> T.enableAndExecute(
    enabled: Provider<Boolean> = provider { true },
    action: Action<T>
) {
    enabled(enabled)
    action.execute(this)
}
