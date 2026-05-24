@file:Suppress("UnstableApiUsage")

package hubdle.ecosystem.feature.documentation.readme

import hubdle.ecosystem.feature.documentation.readme.badges.HubdleDocumentationReadmeBadgesDefinition
import hubdle.platform.HubdleDefinition
import org.gradle.api.tasks.Nested
import org.gradle.declarative.dsl.model.annotations.Adding
import org.gradle.features.binding.Definition

public interface HubdleDocumentationReadmeDefinition :
    HubdleDefinition, Definition<HubdleDocumentationReadmeBuildModel> {

    override val featureName: String
        get() = "readme"

    @get:Nested public val badges: HubdleDocumentationReadmeBadgesDefinition

    @Adding
    public fun badges(enabled: Boolean = true) {
        badges.enabled.set(enabled)
    }
}
