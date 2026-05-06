@file:Suppress("UnstableApiUsage")

package hubdle.declarative.documentation.readme

import hubdle.declarative.documentation.HubdleDocumentationDefinition
import hubdle.platform.HubdleServices
import hubdle.platform.tasks.computeHubdleEffectiveEnabled
import org.gradle.features.binding.ProjectFeatureApplicationContext
import org.gradle.features.binding.ProjectFeatureApplyAction

internal abstract class HubdleDocumentationReadmeApplyAction :
    ProjectFeatureApplyAction<
        HubdleDocumentationReadmeDefinition,
        HubdleDocumentationReadmeBuildModel,
        HubdleDocumentationDefinition,
    >,
    HubdleServices {

    override fun apply(
        context: ProjectFeatureApplicationContext,
        definition: HubdleDocumentationReadmeDefinition,
        buildModel: HubdleDocumentationReadmeBuildModel,
        parentDefinition: HubdleDocumentationDefinition,
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
