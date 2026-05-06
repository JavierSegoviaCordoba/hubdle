@file:Suppress("UnstableApiUsage")

package hubdle.declarative.documentation.readme.badges

import hubdle.platform.HubdleDefinition
import org.gradle.api.provider.Property
import org.gradle.declarative.dsl.model.annotations.HasDefaultValue
import org.gradle.features.binding.Definition

public interface HubdleDocumentationReadmeBadgesDefinition :
    HubdleDefinition, Definition<HubdleDocumentationReadmeBadgesBuildModel> {

    override val featureName: String
        get() = "badges"

    @get:HasDefaultValue public val mainProjectName: Property<String>
    @get:HasDefaultValue public val kotlin: Property<Boolean>
    @get:HasDefaultValue public val mavenCentral: Property<Boolean>
    @get:HasDefaultValue public val snapshots: Property<Boolean>
    @get:HasDefaultValue public val build: Property<Boolean>
    @get:HasDefaultValue public val coverage: Property<Boolean>
    @get:HasDefaultValue public val quality: Property<Boolean>
    @get:HasDefaultValue public val techDebt: Property<Boolean>
}
