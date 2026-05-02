@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config.documentation.readme

import hubdle.platform.HubdleDefinition
import org.gradle.features.binding.Definition

public interface HubdleConfigDocumentationReadmeDefinition :
    HubdleDefinition, Definition<HubdleConfigDocumentationReadmeBuildModel> {

    override val featureName: String
        get() = "readme"
}
