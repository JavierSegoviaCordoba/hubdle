package com.javiersc.hubdle.project.extensions.config.versioning.semver

import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.gradle.properties.extensions.getStringProperty
import com.javiersc.gradle.version.GradleVersion
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.versioning.hubdleVersioning
import com.javiersc.semver.project.gradle.plugin.SemverExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.configure

@HubdleDslMarker
public abstract class HubdleConfigVersioningSemverExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleVersioning)

    override val priority: Priority = Priority.P3

    public val tagPrefix: Property<String> = defaultTagPrefix()

    public fun mapVersion(gradleVersion: (GradleVersion) -> String) {
        configurable { the<SemverExtension>().mapVersion(gradleVersion) }
    }

    @HubdleDslMarker
    public fun semver(action: Action<SemverExtension> = Action {}) {
        userConfigurable { action.execute(the()) }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P1,
            scope = Scope.CurrentProject,
            pluginId = PluginId.JavierscSemverGradlePlugin
        )
        configurable(priority = Priority.P1) {
            configure<SemverExtension> { tagPrefix.set(hubdleSemver.tagPrefix.get()) }
        }
    }

    private fun defaultTagPrefix(): Property<String> = property {
        if (isRootProject) getStringProperty("semver.tagPrefix").orNull ?: "" else ""
    }
}

internal val HubdleEnableableExtension.hubdleSemver: HubdleConfigVersioningSemverExtension
    get() = getHubdleExtension()
