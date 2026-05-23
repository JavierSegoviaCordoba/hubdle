@file:Suppress("UnstableApiUsage")

package hubdle.declarative.documentation

import hubdle.platform.HubdleDefinition
import org.gradle.features.binding.Definition

public interface HubdleDocumentationDefinition :
    HubdleDefinition, Definition<HubdleDocumentationBuildModel> {

    override val featureName: String
        get() = "documentation"
}
