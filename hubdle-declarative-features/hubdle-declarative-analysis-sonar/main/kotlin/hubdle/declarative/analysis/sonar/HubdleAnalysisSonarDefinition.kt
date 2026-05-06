@file:Suppress("UnstableApiUsage")

package hubdle.declarative.analysis.sonar

import hubdle.platform.HubdleDefinition
import org.gradle.api.provider.Property
import org.gradle.features.binding.Definition

public interface HubdleAnalysisSonarDefinition :
    HubdleDefinition, Definition<HubdleAnalysisSonarBuildModel> {

    override val featureName: String
        get() = "sonar"

    public val hostUrl: Property<String>
    public val token: Property<String>
    public val organization: Property<String>
    public val projectKey: Property<String>
    public val projectName: Property<String>
}
