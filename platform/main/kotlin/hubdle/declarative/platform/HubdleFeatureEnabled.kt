package hubdle.declarative.platform

import org.gradle.api.provider.Property

public interface HubdleFeatureEnabled {

    public val enabled: Property<Boolean>

    public val loggingEnabled: Property<Boolean>
}
