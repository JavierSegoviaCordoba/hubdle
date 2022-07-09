@file:Suppress("UnusedReceiverParameter")

package com.javiersc.hubdle.extensions.config.documentation.site

import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.options.EnableableOptions
import com.javiersc.hubdle.extensions.options.RawConfigOptions
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance
import ru.vyarus.gradle.plugin.mkdocs.MkdocsExtension

@HubdleDslMarker
public open class SiteExtension
@Inject
constructor(
    objects: ObjectFactory,
) : EnableableOptions, RawConfigOptions<SiteExtension.RawConfigExtension> {

    override var Project.isEnabled: Boolean
        get() = hubdleState.config.documentation.site.isEnabled
        set(value) = hubdleState.config.documentation.site.run { isEnabled = value }

    private val reports: ReportsExtension = objects.newInstance()

    @HubdleDslMarker
    public fun Project.excludes(vararg excludes: ProjectDependency) {
        hubdleState.config.documentation.site.excludes += excludes
    }

    @HubdleDslMarker
    public fun Project.reports(action: Action<ReportsExtension>) {
        action.execute(reports)
    }

    public open class ReportsExtension {
        public var Project.allTests: Boolean
            get() = hubdleState.config.documentation.site.reports.allTests
            set(value) = hubdleState.config.documentation.site.reports.run { allTests = value }

        public var Project.codeAnalysis: Boolean
            get() = hubdleState.config.documentation.site.reports.codeAnalysis
            set(value) = hubdleState.config.documentation.site.reports.run { codeAnalysis = value }

        public var Project.codeCoverage: Boolean
            get() = hubdleState.config.documentation.site.reports.codeCoverage
            set(value) = hubdleState.config.documentation.site.reports.run { codeCoverage = value }

        public var Project.codeQuality: Boolean
            get() = hubdleState.config.documentation.site.reports.codeQuality
            set(value) = hubdleState.config.documentation.site.reports.run { codeQuality = value }
    }

    override val rawConfig: RawConfigExtension = objects.newInstance()

    @HubdleDslMarker
    override fun Project.rawConfig(action: Action<RawConfigExtension>) {
        action.execute(rawConfig)
    }

    @HubdleDslMarker
    public open class RawConfigExtension {

        public fun Project.mkdocs(action: Action<MkdocsExtension>) {
            hubdleState.config.documentation.site.rawConfig.mkdocs = action
        }
    }
}
