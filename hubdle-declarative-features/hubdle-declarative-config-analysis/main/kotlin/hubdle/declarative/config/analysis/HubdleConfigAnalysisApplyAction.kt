@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config.analysis

import hubdle.declarative.config.HubdleConfigDefinition
import hubdle.platform.HubdleServices
import hubdle.platform.tasks.computeHubdleEffectiveEnabled
import org.gradle.features.binding.ProjectFeatureApplicationContext
import org.gradle.features.binding.ProjectFeatureApplyAction

internal abstract class HubdleConfigAnalysisApplyAction :
    ProjectFeatureApplyAction<
        HubdleConfigAnalysisDefinition,
        HubdleConfigAnalysisBuildModel,
        HubdleConfigDefinition,
    >,
    HubdleServices {

    override fun apply(
        context: ProjectFeatureApplicationContext,
        definition: HubdleConfigAnalysisDefinition,
        buildModel: HubdleConfigAnalysisBuildModel,
        parentDefinition: HubdleConfigDefinition,
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
        const val NAME: String = "analysis"
    }
}
