@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config

import hubdle.platform.HubdleDefinition
import org.gradle.api.provider.Property
import org.gradle.features.binding.Definition

public interface HubdleConfigDefinition : HubdleDefinition, Definition<HubdleConfigBuildModel> {
    override val featureName: String
        get() = "config"

    public val group: Property<String>
}
