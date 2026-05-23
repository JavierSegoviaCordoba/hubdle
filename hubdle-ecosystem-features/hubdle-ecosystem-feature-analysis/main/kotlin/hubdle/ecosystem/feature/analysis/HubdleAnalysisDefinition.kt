@file:Suppress("UnstableApiUsage")

package hubdle.ecosystem.feature.analysis

import hubdle.platform.HubdleDefinition
import org.gradle.features.binding.Definition

public interface HubdleAnalysisDefinition : HubdleDefinition, Definition<HubdleAnalysisBuildModel> {

    override val featureName: String
        get() = "analysis"
}
