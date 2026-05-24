@file:Suppress("UnstableApiUsage")

package hubdle.ecosystem.feature.documentation.changelog

import hubdle.platform.HubdleBuildModel
import org.gradle.api.provider.Property
import org.gradle.features.binding.BuildModel

public interface HubdleDocumentationChangelogBuildModel : HubdleBuildModel, BuildModel {

    override val effectiveEnabled: Property<Boolean>
}
