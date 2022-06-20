package com.javiersc.hubdle.extensions.config.documentation.site

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.the
import ru.vyarus.gradle.plugin.mkdocs.MkdocsExtension

@HubdleDslMarker
public open class SiteExtension
@Inject
constructor(
    objects: ObjectFactory,
) : EnableableOptions, RawConfigOptions<SiteExtension.RawConfigExtension> {

    override var isEnabled: Boolean = IS_ENABLED

    private val reports: ReportsExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.reports(action: Action<ReportsExtension>) {
        action.execute(reports)
        hubdleState.config.documentation.site.reports.allTests = reports.allTests
        hubdleState.config.documentation.site.reports.codeAnalysis = reports.codeAnalysis
        hubdleState.config.documentation.site.reports.codeCoverage = reports.codeCoverage
        hubdleState.config.documentation.site.reports.codeQuality = reports.codeQuality
    }

    public open class ReportsExtension {
        public var allTests: Boolean = ALL_TESTS
        public var codeAnalysis: Boolean = CODE_ANALYSIS
        public var codeCoverage: Boolean = CODE_COVERAGE
        public var codeQuality: Boolean = CODE_QUALITY

        public companion object {
            internal const val ALL_TESTS = true
            internal const val CODE_ANALYSIS = true
            internal const val CODE_COVERAGE = true
            internal const val CODE_QUALITY = true
        }
    }

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        afterEvaluate { action.execute(the()) }
    }

    public open class RawConfigExtension {
        public fun Project.mkdocs(action: Action<MkdocsExtension>) {
            afterEvaluate { action.execute(it.the()) }
        }
    }

    public companion object {
        internal const val IS_ENABLED = false
    }
}
