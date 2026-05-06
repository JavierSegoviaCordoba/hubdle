@file:Suppress("UnstableApiUsage")

package hubdle.declarative.versioning.semver

import hubdle.platform.HubdleBuildModel
import org.gradle.api.provider.Property
import org.gradle.features.binding.BuildModel

public interface HubdleVersioningSemverBuildModel : HubdleBuildModel, BuildModel {

    override val effectiveEnabled: Property<Boolean>
}
