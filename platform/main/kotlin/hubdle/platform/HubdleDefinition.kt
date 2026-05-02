@file:Suppress("UnstableApiUsage")

package hubdle.platform

import org.gradle.api.provider.Property

public interface HubdleDefinition {

    public val featureName: String
    public val enabled: Property<Boolean>
    public val loggingEnabled: Property<Boolean>
}
