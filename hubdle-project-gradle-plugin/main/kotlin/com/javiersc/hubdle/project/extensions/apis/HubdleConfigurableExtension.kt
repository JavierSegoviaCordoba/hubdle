package com.javiersc.hubdle.project.extensions.apis

import com.javiersc.hubdle.project.extensions._internal.Configurable
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import org.gradle.api.Project
import org.gradle.api.provider.Property

public abstract class HubdleConfigurableExtension(
    project: Project,
) : HubdleEnableableExtension(project) {

    internal abstract val priority: Priority

    internal fun configurable(isEnabled: Property<Boolean>, config: Configurable.() -> Unit) {
        configurable(isEnabled, priority, config)
    }

    internal fun configurable(config: Configurable.() -> Unit) {
        configurable(isFullEnabled, priority, config)
    }

    internal abstract fun Project.defaultConfiguration()
}
