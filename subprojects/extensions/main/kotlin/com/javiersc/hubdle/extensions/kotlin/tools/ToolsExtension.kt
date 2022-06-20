package com.javiersc.hubdle.extensions.kotlin.tools

import com.diffplug.gradle.spotless.SpotlessExtension
import com.javiersc.hubdle.extensions.internal.Kotlin
import com.javiersc.hubdle.extensions.internal.PluginIds
import com.javiersc.hubdle.extensions.internal.extensionTracker
import com.javiersc.hubdle.extensions.kotlin.tools.format.FormatExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.newInstance
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

public open class ToolsExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val format: FormatExtension = objects.newInstance()

    public fun Project.format(action: Action<FormatExtension> = Action {}): FormatExtension {
        return configFormat(this, action, this@ToolsExtension)
    }

    // configurations
    private fun configFormat(
        project: Project,
        action: Action<FormatExtension>,
        toolsExtension: ToolsExtension
    ): FormatExtension {
        extensionTracker.put(Kotlin.Tools.Format)

        project.pluginManager.apply(PluginIds.Format.spotless)

        action.execute(format)

        project.configure<SpotlessExtension> {
            kotlin { kotlinExtension ->
                kotlinExtension.target(format.includes)
                kotlinExtension.targetExclude(format.excludes)
                kotlinExtension.ktfmt(format.ktfmtVersion).kotlinlangStyle()
            }
        }

        return toolsExtension.format
    }
}
