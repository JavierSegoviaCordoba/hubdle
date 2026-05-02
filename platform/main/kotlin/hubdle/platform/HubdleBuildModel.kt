@file:Suppress("UnstableApiUsage")

package hubdle.platform

import org.gradle.api.provider.Property

public interface HubdleBuildModel {

    public val effectiveEnabled: Property<Boolean>
}
