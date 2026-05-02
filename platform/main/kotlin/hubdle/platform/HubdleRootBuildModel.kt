@file:Suppress("UnstableApiUsage")

package hubdle.platform

import org.gradle.api.provider.Property
import org.gradle.features.binding.BuildModel

public interface HubdleRootBuildModel : HubdleBuildModel, BuildModel {

    override val effectiveEnabled: Property<Boolean>
}
