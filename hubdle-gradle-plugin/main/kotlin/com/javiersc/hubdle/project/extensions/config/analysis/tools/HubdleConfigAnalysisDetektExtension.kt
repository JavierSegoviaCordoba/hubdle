package com.javiersc.hubdle.project.extensions.config.analysis.tools

import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.allKotlinSrcDirsWithoutBuild
import com.javiersc.hubdle.project.extensions._internal.fallbackAction
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.analysis.hubdleAnalysis
import com.javiersc.hubdle.project.extensions.config.analysis.reports.HubdleConfigAnalysisReportsExtension
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import java.io.File
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.provider.SetProperty
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

public open class HubdleConfigAnalysisDetektExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { true }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleAnalysis)

    public val ignoreFailures: Property<Boolean> = property { true }

    public val includes: SetProperty<String> = setProperty {
        allKotlinSrcDirsWithoutBuild.get().map(File::getPath).toSet()
    }

    @HubdleDslMarker
    public fun includes(vararg includes: String) {
        this.includes.addAll(includes.toList())
    }

    public val excludes: SetProperty<String> = setProperty { setOf("**/build/**") }

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
    public fun detekt(action: Action<DetektExtension> = Action {}): Unit = fallbackAction(action)

    override fun Project.defaultConfiguration() {
        applicablePlugin(scope = Scope.CurrentProject, pluginId = PluginId.Detekt)
        lazyConfigurable { configureDetekt() }
    }

    private fun Project.configureDetekt() {
        val hubdleDetekt = this@HubdleConfigAnalysisDetektExtension
        configure<DetektExtension> {
            val kotlinDirs: Provider<List<File>> =
                allKotlinSrcDirsWithoutBuild.map { files -> files.filter(File::isDirectory) }
            parallel = true
            isIgnoreFailures = hubdleDetekt.ignoreFailures.get()
            buildUponDefaultConfig = true
            basePath = projectDir.path
            source.from(kotlinDirs)
        }

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
