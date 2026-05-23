@file:Suppress("UnstableApiUsage")

package hubdle.ecosystem.feature.documentation

import hubdle.platform.HubdleEcosystemDefinition
import hubdle.platform.HubdleServices
import hubdle.platform.tasks.computeHubdleEffectiveEnabled
import org.gradle.features.binding.ProjectFeatureApplicationContext
import org.gradle.features.binding.ProjectFeatureApplyAction

internal abstract class HubdleDocumentationApplyAction :
    ProjectFeatureApplyAction<
        HubdleDocumentationDefinition,
        HubdleDocumentationBuildModel,
        HubdleEcosystemDefinition,
    >,
    HubdleServices {

    override fun apply(
        context: ProjectFeatureApplicationContext,
        definition: HubdleDocumentationDefinition,
        buildModel: HubdleDocumentationBuildModel,
        parentDefinition: HubdleEcosystemDefinition,
    ) = definition.run {
        val featureEffectiveEnabled =
            context.computeHubdleEffectiveEnabled(
                featureDefinition = definition,
                parentDefinition = parentDefinition,
                defaultEnabled = true,
            )

        if (featureEffectiveEnabled.get()) {
            logLifecycle { "Feature '$featureName' enabled on '${project.path}'" }
        }
    }

    companion object {
        const val NAME: String = "documentation"
    }
}
