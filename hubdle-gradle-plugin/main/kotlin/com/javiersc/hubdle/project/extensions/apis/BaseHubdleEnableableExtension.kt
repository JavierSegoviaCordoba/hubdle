package com.javiersc.hubdle.project.extensions.apis

import org.gradle.api.provider.Property

public interface BaseHubdleEnableableExtension : BaseHubdleExtension {

    public val isEnabled: Property<Boolean>

    public fun enabled(value: Boolean = true) {
        isEnabled.set(value)
    }
}
