@file:Suppress("UnstableApiUsage")

package hubdle.platform

import org.gradle.features.binding.BuildModel
import org.gradle.features.binding.Definition

public interface HubdleRootDefinition : HubdleDefinition, Definition<BuildModel.None> {
    override val featureName: String
        get() = "hubdle"
}
