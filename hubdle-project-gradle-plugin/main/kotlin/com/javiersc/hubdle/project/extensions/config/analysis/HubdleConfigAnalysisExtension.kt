package com.javiersc.hubdle.project.extensions.config.analysis

import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.gradle.properties.extensions.getProperty
import com.javiersc.gradle.properties.extensions.getPropertyOrNull
import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.project.HubdleProperty
import com.javiersc.hubdle.project.extensions.HubdleDslMarker
import com.javiersc.hubdle.project.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.project.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.project.extensions._internal.PluginId
import com.javiersc.hubdle.project.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.project.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.project.extensions.apis.enableAndExecute
import com.javiersc.hubdle.project.extensions.config.analysis.reports.HubdleConfigAnalysisReportsExtension
import com.javiersc.hubdle.project.extensions.config.hubdleConfig
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import java.io.File
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
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

    public val includes: SetProperty<String> = setProperty { setOf("**/*.kt", "**/*.kts") }

    public val excludes: SetProperty<String> = setProperty {
        setOf("**/resources/**", "**/build/**")
    }

    @HubdleDslMarker
    public fun includes(vararg paths: String) {
        includes.addAll(paths.toList())
    }

    @HubdleDslMarker
    public fun excludes(vararg paths: String) {
        excludes.addAll(paths.toList())
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

        configurable {
            // TODO: Fix do this per project
            check(project.isRootProject) {
                """Hubdle `analysis()` must be only configured in the root project"""
            }
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
            project.configure<DetektExtension> {
                parallel = true
                isIgnoreFailures = hubdleAnalysis.ignoreFailures.get()
                buildUponDefaultConfig = true
                basePath = project.rootProject.projectDir.path
            }

            project.tasks.namedLazily<Task>("checkAnalysis").configureEach { task ->
                task.dependsOn("detekt")
            }

            project.tasks.withType<Detekt>().configureEach { detekt ->
                detekt.setSource(project.files(project.rootDir))
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

    private fun configureSonarqube(project: Project) {
        // project.tasks.namedLazily<Task>("sonarqube").configureEach { it.dependsOn("detekt") }

        // project.tasks.namedLazily<Task>("checkAnalysis").configureEach {
        // it.dependsOn("sonarqube") }

        project.configure<SonarExtension> {
            properties { properties ->
                properties.property(
                    "sonar.projectName",
                    project.getPropertyOrNull(HubdleProperty.Analysis.projectName)
                        ?: project.getPropertyOrNull(HubdleProperty.Project.rootProjectDirName)
                            ?: project.name
                )
                properties.property(
                    "sonar.projectKey",
                    project.getPropertyOrNull(HubdleProperty.Analysis.projectKey)
                        ?: project.getPropertyOrNull(HubdleProperty.Project.rootProjectDirName)
                            ?: "${project.group}:${project.name}"
                )
                properties.property(
                    "sonar.login",
                    project.getProperty(HubdleProperty.Analysis.login),
                )
                properties.property(
                    "sonar.host.url",
                    project.getPropertyOrNull(HubdleProperty.Analysis.hostUrl)
                        ?: "https://sonarcloud.io"
                )

                properties.property(
                    "sonar.organization",
                    project.getPropertyOrNull(HubdleProperty.Analysis.organization) ?: ""
                )
                properties.property(
                    "sonar.kotlin.detekt.reportPaths",
                    "${project.buildDir}/reports/detekt/detekt.xml"
                )
                properties.property(
                    "sonar.coverage.jacoco.xmlReportPaths",
                    "${project.buildDir}/reports/kover/report.xml"
                )
            }
        }

        project.allprojects { allproject ->
            allproject.afterEvaluate {
                allproject.extensions.findByType<SonarExtension>()?.apply {
                    properties { properties ->
                        properties.property("sonar.sources", allproject.kotlinSrcDirs())
                        properties.property("sonar.tests", allproject.kotlinTestsSrcDirs())
                    }
                }
            }
        }
    }

    private fun Project.kotlinSrcDirs(): Set<File> =
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
            .orEmpty()
            .toSet()

    private fun Project.kotlinTestsSrcDirs(): Set<File> =
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
            .orEmpty()
            .toSet()
}

internal val HubdleEnableableExtension.hubdleAnalysis: HubdleConfigAnalysisExtension
    get() = getHubdleExtension()
