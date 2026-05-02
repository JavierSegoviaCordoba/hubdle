@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config.documentation.readme

import hubdle.declarative.config.documentation.HubdleConfigDocumentationDefinition
import hubdle.platform.HubdleServices
import hubdle.platform.tasks.computeHubdleEffectiveEnabled
import org.gradle.features.binding.ProjectFeatureApplicationContext
import org.gradle.features.binding.ProjectFeatureApplyAction

internal abstract class HubdleConfigDocumentationReadmeApplyAction :
    ProjectFeatureApplyAction<
        HubdleConfigDocumentationReadmeDefinition,
        HubdleConfigDocumentationReadmeBuildModel,
        HubdleConfigDocumentationDefinition,
    >,
    HubdleServices {

    override fun apply(
        context: ProjectFeatureApplicationContext,
        definition: HubdleConfigDocumentationReadmeDefinition,
        buildModel: HubdleConfigDocumentationReadmeBuildModel,
        parentDefinition: HubdleConfigDocumentationDefinition,
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
        const val NAME: String = "readme"
    }
}
