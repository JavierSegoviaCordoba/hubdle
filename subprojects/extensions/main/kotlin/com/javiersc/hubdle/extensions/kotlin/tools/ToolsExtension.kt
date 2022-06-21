package com.javiersc.hubdle.extensions.kotlin.tools

import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.tools.format.FormatExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

public open class ToolsExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val format: FormatExtension = objects.newInstance()

    public fun Project.format(action: Action<FormatExtension> = Action {}) {
        return configFormat(action)
    }

    // configurations
    private fun Project.configFormat(action: Action<FormatExtension>) {
        project.pluginManager.apply(PluginIds.Format.spotless)
        action.execute(format)
        hubdleState.kotlin.tools.format.apply {
            isEnabled = true
            includes += format.includes
            excludes += format.includes
            ktfmtVersion = format.ktfmtVersion
        }
    }
}
