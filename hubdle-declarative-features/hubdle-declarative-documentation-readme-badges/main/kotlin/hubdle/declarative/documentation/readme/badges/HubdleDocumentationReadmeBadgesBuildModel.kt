@file:Suppress("UnstableApiUsage")

package hubdle.declarative.documentation.readme.badges

import hubdle.platform.HubdleBuildModel
import org.gradle.api.provider.Property
import org.gradle.features.binding.BuildModel

public interface HubdleDocumentationReadmeBadgesBuildModel : HubdleBuildModel, BuildModel {

    override val effectiveEnabled: Property<Boolean>
}
