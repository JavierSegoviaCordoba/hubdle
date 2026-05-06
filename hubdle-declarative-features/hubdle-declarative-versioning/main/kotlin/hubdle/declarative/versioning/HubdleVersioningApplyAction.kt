@file:Suppress("UnstableApiUsage")

package hubdle.declarative.versioning

import hubdle.platform.HubdleRootDefinition
import hubdle.platform.HubdleServices
import hubdle.platform.tasks.computeHubdleEffectiveEnabled
import org.gradle.features.binding.ProjectFeatureApplicationContext
import org.gradle.features.binding.ProjectFeatureApplyAction

internal abstract class HubdleVersioningApplyAction :
    ProjectFeatureApplyAction<
        HubdleVersioningDefinition,
        HubdleVersioningBuildModel,
        HubdleRootDefinition,
    >,
    HubdleServices {

    override fun apply(
        context: ProjectFeatureApplicationContext,
        definition: HubdleVersioningDefinition,
        buildModel: HubdleVersioningBuildModel,
        parentDefinition: HubdleRootDefinition,
    ) = definition.run {
        val featureEffectiveEnabled =
            context.computeHubdleEffectiveEnabled(
                featureDefinition = definition,
                parentDefinition = parentDefinition,
                defaultEnabled = false,
            )

        if (featureEffectiveEnabled.get()) {
            logLifecycle { "Feature '$featureName' enabled on '${project.path}'" }
        }
    }

    companion object {
        const val NAME: String = "versioning"
    }
}
