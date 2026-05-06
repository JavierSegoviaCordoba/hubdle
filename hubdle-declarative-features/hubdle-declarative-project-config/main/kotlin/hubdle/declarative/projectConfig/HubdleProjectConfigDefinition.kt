@file:Suppress("UnstableApiUsage")

package hubdle.declarative.projectConfig

import hubdle.platform.HubdleDefinition
import org.gradle.api.provider.Property
import org.gradle.declarative.dsl.model.annotations.HasDefaultValue
import org.gradle.features.binding.Definition

public interface HubdleProjectConfigDefinition :
    HubdleDefinition, Definition<HubdleProjectConfigBuildModel> {
    override val featureName: String
        get() = "projectConfig"

    @get:HasDefaultValue
    public val group: Property<String>
}
