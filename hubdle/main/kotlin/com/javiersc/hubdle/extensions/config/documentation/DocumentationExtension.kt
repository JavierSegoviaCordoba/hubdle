package com.javiersc.hubdle.extensions.config.documentation

import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Project

public abstract class DocumentationExtension {

    public fun Project.changelog() {
        pluginManager.apply(PluginIds.Documentation.changelog)

        hubdleState.config.documentation.changelog.isEnabled = true
    }
}
