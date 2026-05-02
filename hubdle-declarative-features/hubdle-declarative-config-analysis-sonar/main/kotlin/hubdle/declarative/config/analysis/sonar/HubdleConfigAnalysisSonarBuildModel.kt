@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config.analysis.sonar

import hubdle.platform.HubdleBuildModel
import org.gradle.api.provider.Property
import org.gradle.features.binding.BuildModel

public interface HubdleConfigAnalysisSonarBuildModel : HubdleBuildModel, BuildModel {

    override val effectiveEnabled: Property<Boolean>
}
