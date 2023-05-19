package com.javiersc.hubdle.project.extensions.config.analysis.tools

import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.analysis.hubdleAnalysis
import com.javiersc.hubdle.project.extensions.config.analysis.kotlinSrcDirs
import com.javiersc.hubdle.project.extensions.config.analysis.kotlinTestsSrcDirs
import com.javiersc.hubdle.project.extensions.config.analysis.reports.HubdleConfigAnalysisReportsExtension
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

public open class HubdleConfigAnalysisDetektExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val priority: Priority = Priority.P3

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleAnalysis)

    public val ignoreFailures: Property<Boolean> = property { true }

    public val includes: SetProperty<String> = setProperty {
        kotlinSrcDirs.get() + kotlinTestsSrcDirs.get()
    }

    @HubdleDslMarker
    public fun includes(vararg includes: String) {
        this.includes.addAll(includes.toList())
    }

    public val excludes: SetProperty<String> = setProperty { emptySet() }

    @HubdleDslMarker
    public fun excludes(vararg excludes: String) {
        this.excludes.addAll(excludes.toList())
    }

    public val reports: HubdleConfigAnalysisReportsExtension
        get() = getHubdleExtension()

    @HubdleDslMarker
    public fun reports(action: Action<HubdleConfigAnalysisReportsExtension> = Action {}) {
        reports.enableAndExecute(action)
    }

    @HubdleDslMarker
    public fun detekt(action: Action<DetektExtension> = Action {}) {
        userConfigurable { action.execute(the()) }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P4,
            scope = ApplicablePlugin.Scope.CurrentProject,
            pluginId = PluginId.Detekt
        )
        configurable { configureDetekt() }
    }

    private fun Project.configureDetekt() {
        val hubdleDetekt = this@HubdleConfigAnalysisDetektExtension
        configure<DetektExtension> {
            parallel = true
            isIgnoreFailures = hubdleDetekt.ignoreFailures.get()
            buildUponDefaultConfig = true
            basePath = projectDir.path
            source = files(provider { kotlinSrcDirs.get() + kotlinTestsSrcDirs.get() })
        }

        tasks.namedLazily<Task>("checkAnalysis").configureEach { task -> task.dependsOn("detekt") }

        configureDetektTask()
    }

    private fun Project.configureDetektTask() {
        tasks.withType<Detekt>().configureEach { detekt ->
            detekt.include(includes.get())
            detekt.exclude(excludes.get())

            detekt.reports { detektReports ->
                detektReports.md.required.set(reports.md)
                detektReports.html.required.set(reports.html)
                detektReports.sarif.required.set(reports.sarif)
                detektReports.txt.required.set(reports.txt)
                detektReports.xml.required.set(reports.xml)
            }
        }
    }
}
