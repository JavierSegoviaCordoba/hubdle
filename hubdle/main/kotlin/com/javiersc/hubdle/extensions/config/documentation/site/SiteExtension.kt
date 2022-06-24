package com.javiersc.hubdle.extensions.config.documentation.site

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance

public abstract class SiteExtension
@Inject
constructor(
    objects: ObjectFactory,
) {

    private val reports: ReportsExtension = objects.newInstance()

    public fun Project.reports(action: Action<ReportsExtension>) {
        action.execute(reports)

        hubdleState.config.documentation.site.reports.allTests = reports.allTests
        hubdleState.config.documentation.site.reports.codeAnalysis = reports.codeAnalysis
        hubdleState.config.documentation.site.reports.codeCoverage = reports.codeCoverage
        hubdleState.config.documentation.site.reports.codeQuality = reports.codeQuality
    }

    public open class ReportsExtension {
        public var allTests: Boolean = true
        public var codeAnalysis: Boolean = true
        public var codeCoverage: Boolean = true
        public var codeQuality: Boolean = true
    }
}
