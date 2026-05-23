@file:Suppress("UnstableApiUsage")

package hubdle.ecosystem.feature.kotlin

import hubdle.platform.HubdleBuildModel
import org.gradle.api.provider.Property
import org.gradle.features.binding.BuildModel

public interface HubdleKotlinBuildModel : HubdleBuildModel, BuildModel {

    override val effectiveEnabled: Property<Boolean>
}
