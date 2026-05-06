@file:Suppress("UnstableApiUsage")

package hubdle.declarative.versioning

import hubdle.platform.HubdleDefinition
import org.gradle.features.binding.Definition

public interface HubdleVersioningDefinition :
    HubdleDefinition, Definition<HubdleVersioningBuildModel> {

    override val featureName: String
        get() = "versioning"
}
