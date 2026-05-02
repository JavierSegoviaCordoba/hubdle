@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config.documentation.readme

import hubdle.platform.HubdleBuildModel
import org.gradle.api.provider.Property
import org.gradle.features.binding.BuildModel

public interface HubdleConfigDocumentationReadmeBuildModel : HubdleBuildModel, BuildModel {

    override val effectiveEnabled: Property<Boolean>
}
