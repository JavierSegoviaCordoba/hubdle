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

    override var isEnabled: Boolean = IS_ENABLED

    public var ignoreFailures: Boolean = IGNORE_FAILURES

    public val includes: MutableList<String> = INCLUDES

    public val excludes: MutableList<String> = EXCLUDES

    private val reports: ReportsExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.reports(action: Action<ReportsExtension> = Action {}) {
        action.execute(reports)

        hubdleState.config.analysis.reports.html = reports.html
        hubdleState.config.analysis.reports.sarif = reports.sarif
        hubdleState.config.analysis.reports.txt = reports.txt
        hubdleState.config.analysis.reports.xml = reports.xml
    }

    public open class ReportsExtension {
        public var html: Boolean = HTML
        public var sarif: Boolean = SARIF
        public var txt: Boolean = TXT
        public var xml: Boolean = XML

        public companion object {
            internal const val HTML = true
            internal const val SARIF = true
            internal const val TXT = false
            internal const val XML = true
        }
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

    public companion object {
        internal const val IS_ENABLED = false
        internal const val IGNORE_FAILURES = true
        internal val INCLUDES = mutableListOf("**/*.kt", "**/*.kts")
        internal val EXCLUDES = mutableListOf("**/resources/**", "**/build/**")
    }
}
