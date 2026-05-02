@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config.documentation.readme.badges

import hubdle.platform.HubdleBuildModel
import org.gradle.api.provider.Property
import org.gradle.features.binding.BuildModel

public interface HubdleConfigDocumentationReadmeBadgesBuildModel : HubdleBuildModel, BuildModel {

    override val effectiveEnabled: Property<Boolean>
}
