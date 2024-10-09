package com.javiersc.hubdle.project.extensions.config.publishing.gradle.portal

import com.gradle.publish.PublishTask
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.config.publishing.hubdlePublishing
import com.javiersc.hubdle.project.extensions.config.publishing.tasks.CheckIsSemverTask
import com.javiersc.hubdle.project.extensions.config.versioning.semver._internal.isTagPrefixProject
import com.javiersc.hubdle.project.extensions.shared.PluginId
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.withType

@HubdleDslMarker
public open class HubdleConfigPublishingGradlePortalExtension
@Inject
constructor(project: Project) : HubdleEnableableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdlePublishing)
}

internal fun HubdleConfigurableExtension.configurableGradlePortalPublishing(
    additionalConfig: Configurable.() -> Unit = {}
) {
    val isEnabled: Property<Boolean> = property {
        isEnabled.get() && hubdlePublishingGradlePortal.isFullEnabled.get()
    }

    applicablePlugin(
        isEnabled = isEnabled,
        scope = Scope.CurrentProject,
        pluginId = PluginId.GradlePluginPublish,
    )

    lazyConfigurable(isEnabled = isEnabled) {
        tasks.withType<PublishTask>().configureEach { task ->
            task.enabled = isTagPrefixProject
            task.dependsOn(CheckIsSemverTask.NAME)
        }
        additionalConfig()
    }
}

internal val Project.hubdlePublishingGradlePortal: HubdleConfigPublishingGradlePortalExtension
    get() = getHubdleExtension()
