@file:Suppress("UnstableApiUsage")

package hubdle.declarative.analysis.sonar

import hubdle.platform.HubdleDefinition
import org.gradle.api.provider.Property
import org.gradle.declarative.dsl.model.annotations.HasDefaultValue
import org.gradle.features.binding.Definition

public interface HubdleAnalysisSonarDefinition :
    HubdleDefinition, Definition<HubdleAnalysisSonarBuildModel> {

    override val featureName: String
        get() = "sonar"

    @get:HasDefaultValue public val hostUrl: Property<String>
    @get:HasDefaultValue public val token: Property<String>
    @get:HasDefaultValue public val organization: Property<String>
    @get:HasDefaultValue public val projectKey: Property<String>
    @get:HasDefaultValue public val projectName: Property<String>
}
