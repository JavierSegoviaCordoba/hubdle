@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import com.javiersc.kotlin.stdlib.isNotNullNorBlank
import hubdle.platform.HubdleRootDefinition
import hubdle.platform.HubdleServices
import hubdle.platform.tasks.computeHubdleEffectiveEnabled
import org.gradle.api.provider.Provider
import org.gradle.features.binding.ProjectFeatureApplicationContext
import org.gradle.features.binding.ProjectFeatureApplyAction

internal abstract class HubdleConfigApplyAction :
    ProjectFeatureApplyAction<HubdleConfigDefinition, HubdleConfigBuildModel, HubdleRootDefinition>,
    HubdleServices {

    override fun apply(
        context: ProjectFeatureApplicationContext,
        definition: HubdleConfigDefinition,
        buildModel: HubdleConfigBuildModel,
        parentDefinition: HubdleRootDefinition,
    ) = definition.run {
        val featureEffectiveEnabled: Provider<Boolean> =
            context.computeHubdleEffectiveEnabled(
                featureDefinition = definition,
                parentDefinition = parentDefinition,
                defaultEnabled = true,
            )

        val isEnabled: Boolean = featureEffectiveEnabled.get()

        if (isEnabled) {
            logLifecycle { "Feature '$featureName' enabled on '${project.path}'" }

            definition.setProjectGroup()
        }
    }

    private fun HubdleConfigDefinition.setProjectGroup() {
        val group: String? = group.orNull
        if (group.isNotNullNorBlank()) {
            project.group = group
            logLifecycle { "Project '${project.path}' has changed the group to '${project.group}'" }
        } else {
            logLifecycle {
                "Project '${project.path}' is using the default project group '${project.group}'"
            }
        }
    }

    companion object {
        const val NAME = "config"
    }
}
