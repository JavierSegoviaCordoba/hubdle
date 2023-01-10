package com.javiersc.hubdle.extensions.shared.features.gradle

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.PluginId
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.BaseHubdleDelegateExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.apis.enableAndExecute
import com.javiersc.hubdle.extensions.kotlin.jvm.hubdleKotlinJvm
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension

public open class HubdleGradlePluginFeatureExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val oneOfExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleKotlinJvm)

    override val priority: Priority = Priority.P4

    public val tags: SetProperty<String> = setProperty { emptySet() }

    @HubdleDslMarker
    public fun tags(vararg tags: String) {
        this.tags.addAll(tags.toList())
    }

    @HubdleDslMarker
    public fun gradlePlugin(action: Action<GradlePluginDevelopmentExtension>) {
        userConfigurable { action.execute(the()) }
    }

    public val pluginUnderTestDependencies: ListProperty<MinimalExternalModuleDependency> =
        listProperty {
            emptyList()
        }

    @HubdleDslMarker
    public fun pluginUnderTestDependencies(
        vararg pluginUnderTestDependencies: MinimalExternalModuleDependency
    ) {
        this.pluginUnderTestDependencies.addAll(pluginUnderTestDependencies.toList())
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P3,
            scope = Scope.CurrentProject,
            pluginId = PluginId.GradleApplication
        )
    }
}

public interface HubdleGradlePluginDelegateFeatureExtension : BaseHubdleDelegateExtension {

    public val plugin: HubdleGradlePluginFeatureExtension
        get() = project.getHubdleExtension()

    @HubdleDslMarker
    public fun plugin(action: Action<HubdleGradlePluginFeatureExtension> = Action {}) {
        plugin.enableAndExecute(action)
    }
}

internal val HubdleEnableableExtension.hubdleGradlePluginFeature: HubdleGradlePluginFeatureExtension
    get() = getHubdleExtension()
