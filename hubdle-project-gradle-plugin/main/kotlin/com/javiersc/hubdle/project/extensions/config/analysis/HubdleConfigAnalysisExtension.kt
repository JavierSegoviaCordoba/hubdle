package com.javiersc.hubdle.project.extensions.config.analysis

import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.gradle.properties.extensions.getProperty
import com.javiersc.gradle.properties.extensions.getPropertyOrNull
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.project.HubdleProperty
import com.javiersc.hubdle.project.HubdleProperty.Analysis.Sonar
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions._internal.setProperty
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.analysis.reports.HubdleConfigAnalysisReportsExtension
import com.javiersc.hubdle.project.extensions.config.hubdleConfig
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import io.gitlab.arturbosch.detekt.report.ReportMergeTask
import java.io.File
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.TaskCollection
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.sonarqube.gradle.SonarExtension

@HubdleDslMarker
public open class HubdleConfigAnalysisExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleConfig)

    override val priority: Priority = Priority.P3

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
            scope = Scope.CurrentProject,
            pluginId = PluginId.Detekt
        )

        applicablePlugin(
            priority = Priority.P4,
            scope = Scope.CurrentProject,
            pluginId = PluginId.Sonarqube
        )

        configurable(priority = Priority.P4) {
            val checkAnalysisTask = project.tasks.maybeRegisterLazily<Task>("checkAnalysis")
            checkAnalysisTask.configureEach { task -> task.group = "verification" }
            project.tasks.namedLazily<Task>("check").configureEach { task ->
                task.dependsOn(checkAnalysisTask)
            }
            configureDetekt(project)
            configureSonarqube(project)
        }
    }

    private fun configureDetekt(project: Project) =
        with(project) {
            configure<DetektExtension> {
                parallel = true
                isIgnoreFailures = hubdleAnalysis.ignoreFailures.get()
                buildUponDefaultConfig = true
                basePath = project.projectDir.path
                source = files(provider { kotlinSrcDirs.get() + kotlinTestsSrcDirs.get() })
            }

            tasks.namedLazily<Task>("checkAnalysis").configureEach { task ->
                task.dependsOn("detekt")
            }

            configureDetektTask(project)
            configureMergeDetektReports(project)
        }

    private fun configureDetektTask(project: Project) {
        project.tasks.withType<Detekt>().configureEach { detekt ->
            detekt.include(hubdleAnalysis.includes.get())
            detekt.exclude(hubdleAnalysis.excludes.get())

            detekt.reports { reports ->
                reports.md.required.set(hubdleAnalysis.reports.md)
                reports.html.required.set(hubdleAnalysis.reports.html)
                reports.sarif.required.set(hubdleAnalysis.reports.sarif)
                reports.txt.required.set(hubdleAnalysis.reports.txt)
                reports.xml.required.set(hubdleAnalysis.reports.xml)
            }
        }
    }

    private fun configureMergeDetektReports(project: Project) {
        if (project.isRootProject) {
            val buildDirectory: DirectoryProperty = project.layout.buildDirectory
            val detektReportMergeHtml: TaskProvider<ReportMergeTask> =
                tasks.register<ReportMergeTask>("detektReportMergeHtml") {
                    output.set(buildDirectory.file("reports/detekt/detekt.html"))
                }
            val detektReportMergeMd: TaskProvider<ReportMergeTask> =
                tasks.register<ReportMergeTask>("detektReportMergeMd") {
                    output.set(buildDirectory.file("reports/detekt/detekt.md"))
                }
            val detektReportMergeSarif: TaskProvider<ReportMergeTask> =
                tasks.register<ReportMergeTask>("detektReportMergeSarif") {
                    output.set(buildDirectory.file("reports/detekt/detekt.sarif"))
                }
            val detektReportMergeTxt: TaskProvider<ReportMergeTask> =
                tasks.register<ReportMergeTask>("detektReportMergeTxt") {
                    output.set(buildDirectory.file("reports/detekt/detekt.txt"))
                }
            val detektReportMergeXml: TaskProvider<ReportMergeTask> =
                tasks.register<ReportMergeTask>("detektReportMergeXml") {
                    output.set(buildDirectory.file("reports/detekt/detekt.xml"))
                }

            project.subprojects { subproject ->
                val detektTasks: TaskCollection<Detekt> = subproject.tasks.withType<Detekt>()
                detektTasks.configureEach { detekt ->
                    detekt.finalizedBy(detektReportMergeHtml)
                    detekt.finalizedBy(detektReportMergeMd)
                    detekt.finalizedBy(detektReportMergeSarif)
                    detekt.finalizedBy(detektReportMergeTxt)
                    detekt.finalizedBy(detektReportMergeXml)
                    detektReportMergeHtml.configure { it.input.from(detekt.htmlReportFile) }
                    detektReportMergeMd.configure { it.input.from(detekt.mdReportFile) }
                    detektReportMergeSarif.configure { it.input.from(detekt.sarifReportFile) }
                    detektReportMergeTxt.configure { it.input.from(detekt.txtReportFile) }
                    detektReportMergeXml.configure { it.input.from(detekt.xmlReportFile) }
                }
            }
        }
    }

    private fun configureSonarqube(project: Project) {
        project.configure<SonarExtension> {
            properties { properties ->
                properties.property(
                    "sonar.projectName",
                    project.getPropertyOrNull(Sonar.projectName)
                        ?: project.getPropertyOrNull(HubdleProperty.Project.rootProjectDirName)
                            ?: project.name
                )
                properties.property(
                    "sonar.projectKey",
                    project.getPropertyOrNull(Sonar.projectKey)
                        ?: project.getPropertyOrNull(HubdleProperty.Project.rootProjectDirName)
                            ?: "${project.group}:${project.name}"
                )
                properties.property(
                    "sonar.login",
                    project.getProperty(Sonar.login),
                )
                properties.property(
                    "sonar.host.url",
                    project.getPropertyOrNull(Sonar.hostUrl) ?: "https://sonarcloud.io"
                )
                properties.property(
                    "sonar.organization",
                    project.getPropertyOrNull(Sonar.organization) ?: ""
                )
                properties.property(
                    "sonar.kotlin.detekt.reportPaths",
                    "${project.buildDir}/reports/detekt/detekt.xml"
                )
                properties.property(
                    "sonar.coverage.jacoco.xmlReportPaths",
                    "${project.buildDir}/reports/kover/report.xml"
                )

                // TODO: use `includes` and `excludes` from Hubdle when Detekt aggregated reports is
                //  fixed: https://github.com/detekt/detekt/issues/5041
                properties.property("sonar.sources", project.kotlinSrcDirs.get())
                properties.property("sonar.tests", project.kotlinTestsSrcDirs.get())
            }
        }
    }

    private val Project.kotlinSrcDirs: SetProperty<String>
        get() =
            this.setProperty {
                extensions
                    .findByType<KotlinProjectExtension>()
                    ?.sourceSets
                    ?.flatMap { kotlinSourceSet -> kotlinSourceSet.kotlin.srcDirs }
                    ?.filterNot { file ->
                        val relativePath = file.relativeTo(projectDir)
                        val dirs = relativePath.path.split(File.separatorChar)
                        dirs.any { dir -> dir.endsWith("Test") || dir == "test" }
                    }
                    ?.filter { file -> file.exists() }
                    ?.mapNotNull(File::getPath)
                    .orEmpty()
                    .toSet()
            }

    private val Project.kotlinTestsSrcDirs: SetProperty<String>
        get() =
            this.setProperty {
                extensions
                    .findByType<KotlinProjectExtension>()
                    ?.sourceSets
                    ?.flatMap { kotlinSourceSet -> kotlinSourceSet.kotlin.srcDirs }
                    ?.filter { file ->
                        val relativePath = file.relativeTo(projectDir)
                        val dirs = relativePath.path.split(File.separatorChar)
                        dirs.any { dir -> dir.endsWith("Test") || dir == "test" }
                    }
                    ?.filter { file -> file.exists() }
                    ?.mapNotNull(File::getPath)
                    .orEmpty()
                    .toSet()
            }
}

internal val HubdleEnableableExtension.hubdleAnalysis: HubdleConfigAnalysisExtension
    get() = getHubdleExtension()
