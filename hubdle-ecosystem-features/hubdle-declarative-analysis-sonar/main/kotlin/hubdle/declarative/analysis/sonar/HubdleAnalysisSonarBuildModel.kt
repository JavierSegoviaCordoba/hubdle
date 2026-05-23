@file:Suppress("UnstableApiUsage")

package hubdle.declarative.analysis.sonar

import hubdle.platform.HubdleBuildModel
import org.gradle.api.provider.Property
import org.gradle.features.binding.BuildModel

public interface HubdleAnalysisSonarBuildModel : HubdleBuildModel, BuildModel {

    override val effectiveEnabled: Property<Boolean>
}
