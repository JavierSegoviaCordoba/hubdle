@file:Suppress("UnstableApiUsage")

package hubdle.declarative.versioning.semver

import com.javiersc.semver.project.gradle.plugin.SemverExtension
import hubdle.declarative.versioning.HubdleVersioningDefinition
import hubdle.platform.HubdleProperties
import hubdle.platform.HubdleServices
import hubdle.platform.PluginIds
import hubdle.platform.applyPlugin
import hubdle.platform.configure
import hubdle.platform.tasks.computeHubdleEffectiveEnabled
import org.gradle.api.provider.Provider
import org.gradle.features.binding.ProjectFeatureApplicationContext
import org.gradle.features.binding.ProjectFeatureApplyAction

internal abstract class HubdleVersioningSemverApplyAction :
    ProjectFeatureApplyAction<
        HubdleVersioningSemverDefinition,
        HubdleVersioningSemverBuildModel,
        HubdleVersioningDefinition,
    >,
    HubdleServices {

    override fun apply(
        context: ProjectFeatureApplicationContext,
        definition: HubdleVersioningSemverDefinition,
        buildModel: HubdleVersioningSemverBuildModel,
        parentDefinition: HubdleVersioningDefinition,
    ) = definition.run {
        val featureEffectiveEnabled: Provider<Boolean> =
            context.computeHubdleEffectiveEnabled(
                featureDefinition = definition,
                parentDefinition = parentDefinition,
                defaultEnabled = true,
            )

        if (featureEffectiveEnabled.get()) {
            logLifecycle { "Feature '$featureName' enabled on '${project.path}'" }

            definition.configureSemver()
        }
    }

    private fun HubdleVersioningSemverDefinition.configureSemver() {
        applyPlugin(PluginIds.JavierscSemver)

        val definition: HubdleVersioningSemverDefinition = this
        configure<SemverExtension> {
            tagPrefix.set(definition.tagPrefix.orElse(defaultTagPrefix()))
            commitsMaxCount.set(definition.commitsMaxCount.orElse(DefaultCommitsMaxCount))
            if (definition.gitDir.isPresent) {
                gitDir.set(definition.gitDir)
            }
        }
    }

    private fun defaultTagPrefix(): Provider<String> = providers.provider {
        if (project == project.rootProject) {
            providers.gradleProperty(HubdleProperties.Semver.TagPrefix).orNull ?: DefaultTagPrefix
        } else {
            DefaultTagPrefix
        }
    }

    companion object {
        const val NAME: String = "semver"

        private const val DefaultTagPrefix = ""
        private const val DefaultCommitsMaxCount = -1
    }
}
