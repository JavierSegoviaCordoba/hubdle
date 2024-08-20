package com.javiersc.hubdle.project.extensions.config.versioning.semver

import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.BaseHubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.versioning.hubdleVersioning
import com.javiersc.hubdle.project.extensions.shared.PluginId
import com.javiersc.semver.project.gradle.plugin.SemverExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the

@HubdleDslMarker
public abstract class HubdleConfigVersioningSemverExtension @Inject constructor(project: Project) :
    HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleVersioning)

    public val tagPrefix: Property<String> = defaultTagPrefix()

    public fun mapVersion(action: SemverExtension.VersionMapper) {
        withSemverPlugin { it.mapVersion(action) }
    }

    @HubdleDslMarker
    public fun semver(action: Action<SemverExtension> = Action {}): Unit = withSemverPlugin(action)

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            scope = Scope.CurrentProject,
            pluginId = PluginId.JavierscSemverGradlePlugin,
        )
        withSemverPlugin {
            configure<SemverExtension> { //
                tagPrefix.set(hubdleSemver.tagPrefix)
            }
        }
    }

    private fun defaultTagPrefix(): Property<String> = property {
        if (isRootProject) getStringProperty("semver.tagPrefix").orNull ?: "" else ""
    }
}

internal fun BaseHubdleEnableableExtension.withSemverPlugin(action: Action<SemverExtension>) {
    withPlugin(pluginId = PluginId.JavierscSemverGradlePlugin) { action.execute(project.the()) }
}

internal val HubdleEnableableExtension.hubdleSemver: HubdleConfigVersioningSemverExtension
    get() = getHubdleExtension()
