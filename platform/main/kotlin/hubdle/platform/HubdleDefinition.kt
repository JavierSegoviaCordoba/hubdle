@file:Suppress("UnstableApiUsage")

package hubdle.platform

import org.gradle.api.provider.Property
import org.gradle.declarative.dsl.model.annotations.HasDefaultValue

public interface HubdleDefinition {

    public val featureName: String
    @get:HasDefaultValue
    public val enabled: Property<Boolean>
    @get:HasDefaultValue
    public val loggingEnabled: Property<Boolean>
}
