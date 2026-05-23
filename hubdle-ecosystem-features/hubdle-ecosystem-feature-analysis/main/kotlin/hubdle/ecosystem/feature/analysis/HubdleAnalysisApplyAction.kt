@file:Suppress("UnstableApiUsage")

package hubdle.ecosystem.feature.analysis

import hubdle.platform.HubdleEcosystemDefinition
import hubdle.platform.HubdleServices
import hubdle.platform.tasks.computeHubdleEffectiveEnabled
import org.gradle.features.binding.ProjectFeatureApplicationContext
import org.gradle.features.binding.ProjectFeatureApplyAction

internal abstract class HubdleAnalysisApplyAction :
    ProjectFeatureApplyAction<
        HubdleAnalysisDefinition,
        HubdleAnalysisBuildModel,
        HubdleEcosystemDefinition,
    >,
    HubdleServices {

    override fun apply(
        context: ProjectFeatureApplicationContext,
        definition: HubdleAnalysisDefinition,
        buildModel: HubdleAnalysisBuildModel,
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
        const val NAME: String = "analysis"
    }
}
