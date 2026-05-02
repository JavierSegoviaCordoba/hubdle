@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config.documentation

import hubdle.platform.HubdleDefinition
import org.gradle.features.binding.Definition

public interface HubdleConfigDocumentationDefinition :
    HubdleDefinition, Definition<HubdleConfigDocumentationBuildModel> {

    override val featureName: String
        get() = "documentation"
}
