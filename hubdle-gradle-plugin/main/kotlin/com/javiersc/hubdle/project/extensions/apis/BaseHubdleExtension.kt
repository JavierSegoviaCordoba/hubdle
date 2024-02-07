package com.javiersc.hubdle.project.extensions.apis

import com.javiersc.gradle.project.extensions.withPlugins
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.AppliedPlugin

public interface BaseHubdleExtension {

    public val project: Project

    public fun withPlugin(pluginId: String, action: Action<AppliedPlugin>) {
        project.pluginManager.withPlugin(pluginId, action)
    }

    public fun withPlugins(vararg pluginIds: String, action: Project.() -> Unit) {
        project.withPlugins(pluginIds = pluginIds, action = action)
    }
}
