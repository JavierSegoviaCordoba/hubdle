package com.javiersc.hubdle.extensions.kotlin.tools

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.kotlin.tools.analysis.AnalysisExtension
import com.javiersc.hubdle.extensions.kotlin.tools.binary.compatibility.validator.BinaryCompatibilityValidatorExtension
import com.javiersc.hubdle.extensions.kotlin.tools.coverage.CoverageExtension
import com.javiersc.hubdle.extensions.kotlin.tools.format.FormatExtension
import com.javiersc.hubdle.extensions.kotlin.tools.publishing.PublishingExtension
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

    private val binaryCompatibilityValidator: BinaryCompatibilityValidatorExtension =
        objects.newInstance()

    @HubdleDslMarker
    public fun Project.binaryCompatibilityValidator() {
        binaryCompatibilityValidator.isEnabled = true
        hubdleState.kotlin.tools.binaryCompatibilityValidator.isEnabled =
            binaryCompatibilityValidator.isEnabled
    }

    private val coverage: CoverageExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.coverage(action: Action<CoverageExtension> = Action {}) {
        return configCoverage(action)
    }

    @HubdleDslMarker
    public fun Project.explicitApi(explicitApiMode: ExplicitApiMode = ExplicitApiMode.Strict) {
        hubdleState.kotlin.tools.explicitApiMode = explicitApiMode
    }

    private val format: FormatExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.format(action: Action<FormatExtension> = Action {}) {
        return configFormat(action)
    }

    private val publishing: PublishingExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.publishing(action: Action<PublishingExtension> = Action {}) {
        publishing.isEnabled = true
        action.execute(publishing)
        hubdleState.kotlin.isPublishingEnabled = publishing.isEnabled
    }

    // configurations
    private fun Project.configAnalysis(action: Action<AnalysisExtension>) {
        analysis.isEnabled = true
        action.execute(analysis)
        hubdleState.kotlin.tools.analysis.isEnabled = analysis.isEnabled
        hubdleState.kotlin.tools.analysis.includes += analysis.includes
        hubdleState.kotlin.tools.analysis.excludes += analysis.excludes
    }

    private fun Project.configCoverage(action: Action<CoverageExtension>) {
        coverage.isEnabled = true
        action.execute(coverage)
        hubdleState.kotlin.tools.coverage.isEnabled = coverage.isEnabled
    }

    private fun Project.configFormat(action: Action<FormatExtension>) {
        format.isEnabled = true
        action.execute(format)
        hubdleState.kotlin.tools.format.isEnabled = format.isEnabled
        hubdleState.kotlin.tools.format.includes += format.includes
        hubdleState.kotlin.tools.format.excludes += format.excludes
        hubdleState.kotlin.tools.format.ktfmtVersion = format.ktfmtVersion
    }
}
