package com.javiersc.hubdle.extensions.kotlin.tools.analysis

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

@HubdleDslMarker
public abstract class AnalysisExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    public val includes: MutableList<String> =
        mutableListOf(
            "**/*.kt",
            "**/*.kts",
        )

    public val excludes: MutableList<String> =
        mutableListOf(
            "**/resources/**",
            "**/build/**",
        )

    private val reports: ReportsExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.reports(action: Action<ReportsExtension> = Action {}) {
        action.execute(reports)

        hubdleState.kotlin.tools.analysis.apply {
            reports.html = this.reports.html
            reports.sarif = this.reports.sarif
            reports.txt = this.reports.txt
            reports.xml = this.reports.xml
        }
    }

    @HubdleDslMarker
    public abstract class ReportsExtension {
        public var html: Boolean = true
        public var sarif: Boolean = true
        public var txt: Boolean = false
        public var xml: Boolean = true
    }
}
