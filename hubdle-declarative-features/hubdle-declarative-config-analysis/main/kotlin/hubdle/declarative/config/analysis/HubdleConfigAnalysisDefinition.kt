@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config.analysis

import hubdle.platform.HubdleDefinition
import org.gradle.features.binding.Definition

public interface HubdleConfigAnalysisDefinition :
    HubdleDefinition, Definition<HubdleConfigAnalysisBuildModel> {

    override val featureName: String
        get() = "analysis"
}
