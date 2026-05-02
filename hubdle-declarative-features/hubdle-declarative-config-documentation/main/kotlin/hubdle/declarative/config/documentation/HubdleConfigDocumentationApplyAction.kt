@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config.documentation

import hubdle.declarative.config.HubdleConfigDefinition
import hubdle.platform.HubdleServices
import hubdle.platform.tasks.computeHubdleEffectiveEnabled
import org.gradle.features.binding.ProjectFeatureApplicationContext
import org.gradle.features.binding.ProjectFeatureApplyAction

internal abstract class HubdleConfigDocumentationApplyAction :
    ProjectFeatureApplyAction<
        HubdleConfigDocumentationDefinition,
        HubdleConfigDocumentationBuildModel,
        HubdleConfigDefinition,
    >,
    HubdleServices {

    override fun apply(
        context: ProjectFeatureApplicationContext,
        definition: HubdleConfigDocumentationDefinition,
        buildModel: HubdleConfigDocumentationBuildModel,
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
        const val NAME: String = "documentation"
    }
}
