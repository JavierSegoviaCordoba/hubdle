@file:Suppress("UnstableApiUsage")

package hubdle.platform

import org.gradle.features.binding.Definition

public interface HubdleEcosystemDefinition : HubdleDefinition, Definition<HubdleRootBuildModel> {
    override val featureName: String
        get() = "hubdle"
}
