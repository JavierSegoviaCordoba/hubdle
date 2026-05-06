@file:Suppress("UnstableApiUsage")

package hubdle.declarative.versioning.semver

import hubdle.platform.HubdleDefinition
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.declarative.dsl.model.annotations.HasDefaultValue
import org.gradle.features.binding.Definition

public interface HubdleVersioningSemverDefinition :
    HubdleDefinition, Definition<HubdleVersioningSemverBuildModel> {

    override val featureName: String
        get() = "semver"

    @get:HasDefaultValue public val tagPrefix: Property<String>
    @get:HasDefaultValue public val commitsMaxCount: Property<Int>
    @get:HasDefaultValue public val gitDir: DirectoryProperty
}
