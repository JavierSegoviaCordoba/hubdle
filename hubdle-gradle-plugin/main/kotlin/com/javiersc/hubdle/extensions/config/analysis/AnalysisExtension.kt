@file:Suppress("UnusedReceiverParameter")

package com.javiersc.hubdle.extensions.config.analysis

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public open class AnalysisExtension
@Inject
constructor(
    objects: ObjectFactory,
) : EnableableOptions, RawConfigOptions<AnalysisExtension.RawConfigExtension> {

    override var Project.isEnabled: Boolean
        get() = hubdleState.config.analysis.isEnabled
        set(value) = hubdleState.config.analysis.run { isEnabled = value }

    public var Project.ignoreFailures: Boolean
        get() = hubdleState.config.analysis.ignoreFailures
        set(value) = hubdleState.config.analysis.run { ignoreFailures = value }

    public fun Project.includes(vararg includes: String) {
        hubdleState.config.analysis.includes += includes
    }

    public fun Project.excludes(vararg excludes: String) {
        hubdleState.config.analysis.excludes += excludes
    }

    private val reports: ReportsExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.reports(action: Action<ReportsExtension> = Action {}) {
        action.execute(reports)
    }

    public open class ReportsExtension {

        public var Project.html: Boolean
            get() = hubdleState.config.analysis.reports.html
            set(value) = hubdleState.config.analysis.reports.run { html = value }

        public var Project.sarif: Boolean
            get() = hubdleState.config.analysis.reports.sarif
            set(value) = hubdleState.config.analysis.reports.run { sarif = value }

        public var Project.txt: Boolean
            get() = hubdleState.config.analysis.reports.txt
            set(value) = hubdleState.config.analysis.reports.run { txt = value }

        public var Project.xml: Boolean
            get() = hubdleState.config.analysis.reports.xml
            set(value) = hubdleState.config.analysis.reports.run { xml = value }
    }

    override val rawConfig: RawConfigExtension = objects.newInstance()

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        action.execute(rawConfig)
    }

    @HubdleDslMarker
    public open class RawConfigExtension {

        public fun Project.detekt(action: Action<DetektExtension>) {
            hubdleState.config.analysis.rawConfig.detekt = action
        }
    }
}
