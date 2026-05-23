@file:Suppress("UnstableApiUsage")

package hubdle.ecosystem.feature.documentation.readme

import hubdle.platform.HubdleDefinition
import org.gradle.features.binding.Definition

public interface HubdleDocumentationReadmeDefinition :
    HubdleDefinition, Definition<HubdleDocumentationReadmeBuildModel> {

    override val featureName: String
        get() = "readme"
}
