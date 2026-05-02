@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config.documentation.readme.badges

import hubdle.platform.HubdleDefinition
import org.gradle.api.provider.Property
import org.gradle.features.binding.Definition

public interface HubdleConfigDocumentationReadmeBadgesDefinition :
    HubdleDefinition, Definition<HubdleConfigDocumentationReadmeBadgesBuildModel> {

    override val featureName: String
        get() = "badges"

    public val mainProjectName: Property<String>
    public val kotlin: Property<Boolean>
    public val mavenCentral: Property<Boolean>
    public val snapshots: Property<Boolean>
    public val build: Property<Boolean>
    public val coverage: Property<Boolean>
    public val quality: Property<Boolean>
    public val techDebt: Property<Boolean>
}
