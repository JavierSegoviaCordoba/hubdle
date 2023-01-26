package com.javiersc.hubdle.extensions.config.coverage

import com.javiersc.gradle.project.extensions.isRootProject
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions._internal.ApplicablePlugin.Scope
import com.javiersc.hubdle.extensions._internal.Configurable.Priority
import com.javiersc.hubdle.extensions._internal.PluginId
import com.javiersc.hubdle.extensions._internal.getHubdleExtension
import com.javiersc.hubdle.extensions.apis.HubdleConfigurableExtension
import com.javiersc.hubdle.extensions.apis.HubdleEnableableExtension
import com.javiersc.hubdle.extensions.config.hubdleConfig
import com.javiersc.hubdle.extensions.config.testing.ALL_TEST_TASK_NAME
import javax.inject.Inject
import kotlinx.kover.api.CoverageEngineVariant
import kotlinx.kover.api.DefaultJacocoEngine
import kotlinx.kover.api.KoverMergedConfig
import kotlinx.kover.api.KoverProjectConfig
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.the
import org.gradle.language.base.plugins.LifecycleBasePlugin.BUILD_TASK_NAME
import org.gradle.language.base.plugins.LifecycleBasePlugin.CHECK_TASK_NAME

@HubdleDslMarker
public open class HubdleConfigCoverageExtension
@Inject
constructor(
    project: Project,
) : HubdleConfigurableExtension(project) {

    override val isEnabled: Property<Boolean> = property { false }

    override val requiredExtensions: Set<HubdleEnableableExtension>
        get() = setOf(hubdleConfig)

    override val priority: Priority = Priority.P3

    public val engine: Property<CoverageEngineVariant> = property { DefaultJacocoEngine }

    @HubdleDslMarker
    public fun engine(engine: CoverageEngineVariant) {
        this.engine.set(engine)
    }

    @HubdleDslMarker
    public fun kover(action: Action<KoverProjectConfig>) {
        userConfigurable { action.execute(the()) }
    }

    override fun Project.defaultConfiguration() {
        applicablePlugin(
            priority = Priority.P4,
            scope = Scope.AllProjects,
            pluginId = PluginId.JetbrainsKotlinxKover
        )

        configurable {
            check(isRootProject) {
                "Hubdle `coverage()` must be only configured in the root project"
            }
            allprojects { allproject ->
                val hubdleKoverEngine = this@HubdleConfigCoverageExtension.engine
                allproject.the<KoverProjectConfig>().engine.set(hubdleKoverEngine)
                allproject.the<KoverMergedConfig>().enable()
            }
            afterEvaluate {
                val shouldMergeCodeCoverageReports =
                    it.gradle.startParameter.taskNames.any { taskName ->
                        taskName in listOf(ALL_TEST_TASK_NAME, BUILD_TASK_NAME, CHECK_TASK_NAME)
                    } &&
                        it.rootProject.tasks.names.contains(KOVER_MERGED_REPORT_TASK_NAME) &&
                        it.rootProject.tasks.names.contains(ALL_TEST_TASK_NAME)

                if (shouldMergeCodeCoverageReports) {
                    val koverMergedReportTask =
                        it.rootProject.tasks.namedLazily<Task>(KOVER_MERGED_REPORT_TASK_NAME)

                    it.rootProject.tasks.namedLazily<Task>(ALL_TEST_TASK_NAME).configureEach { task
                        ->
                        task.dependsOn(koverMergedReportTask)
                    }
                }
            }
        }
    }
}

private const val KOVER_MERGED_REPORT_TASK_NAME = "koverMergedReport"

internal val HubdleEnableableExtension.hubdleCoverage: HubdleConfigCoverageExtension
    get() = getHubdleExtension()
