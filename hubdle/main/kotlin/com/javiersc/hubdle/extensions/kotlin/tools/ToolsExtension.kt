package com.javiersc.hubdle.extensions.kotlin.tools

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.tools.analysis.AnalysisExtension
import com.javiersc.hubdle.extensions.kotlin.tools.format.FormatExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

@HubdleDslMarker
public open class ToolsExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val analysis: AnalysisExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.analysis(action: Action<AnalysisExtension> = Action {}) {
        return configAnalysis(action)
    }

    public fun Project.binaryCompatibilityValidator() {
        hubdleState.kotlin.tools.binaryCompatibilityValidator = true
    }

    public fun Project.coverage() {
        return configCoverage()
    }

    public fun Project.explicitApi(explicitApiMode: ExplicitApiMode = ExplicitApiMode.Strict) {
        hubdleState.kotlin.tools.explicitApiMode = explicitApiMode
    }

    private val format: FormatExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.format(action: Action<FormatExtension> = Action {}) {
        return configFormat(action)
    }

    // configurations
    private fun Project.configAnalysis(action: Action<AnalysisExtension>) {
        project.pluginManager.apply(PluginIds.Analysis.detekt)
        project.pluginManager.apply(PluginIds.Analysis.sonarqube)
        action.execute(analysis)
        hubdleState.kotlin.tools.analysis.apply {
            isEnabled = true
            includes += analysis.includes
            excludes += analysis.includes
        }
    }

    private fun Project.configCoverage() {
        project.pluginManager.apply(PluginIds.Kotlin.kover)
        hubdleState.kotlin.tools.coverage.isEnabled = true
    }

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
