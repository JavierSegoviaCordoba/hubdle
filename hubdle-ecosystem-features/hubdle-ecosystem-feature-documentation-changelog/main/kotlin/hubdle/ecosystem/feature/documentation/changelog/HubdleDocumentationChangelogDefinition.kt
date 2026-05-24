@file:Suppress("UnstableApiUsage")

package hubdle.ecosystem.feature.documentation.changelog

import hubdle.platform.HubdleDefinition
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.declarative.dsl.model.annotations.HasDefaultValue
import org.gradle.features.binding.Definition

public interface HubdleDocumentationChangelogDefinition :
    HubdleDefinition, Definition<HubdleDocumentationChangelogBuildModel> {

    override val featureName: String
        get() = "changelog"

    @get:HasDefaultValue public val combinePreReleases: Property<Boolean>
    @get:HasDefaultValue public val groups: ListProperty<String>
    @get:HasDefaultValue public val repositoryUrl: Property<String>
    @get:HasDefaultValue public val versionPrefix: Property<String>
}
