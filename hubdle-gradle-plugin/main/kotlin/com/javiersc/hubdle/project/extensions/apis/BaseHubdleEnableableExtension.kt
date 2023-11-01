package com.javiersc.hubdle.project.extensions.apis

import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider

public interface BaseHubdleEnableableExtension : BaseHubdleExtension {

    public val isEnabled: Property<Boolean>

    public fun enabled(value: Boolean = true) {
        isEnabled.set(value)
    }

    public fun enabled(value: Provider<Boolean> = project.provider { true }) {
        isEnabled.set(value)
    }
}
