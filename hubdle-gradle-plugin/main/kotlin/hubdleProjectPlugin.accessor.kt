import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import com.javiersc.hubdle.extensions.HubdleDslMarker
import com.javiersc.hubdle.extensions.HubdleExtension
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.isAndroidApplication
import com.javiersc.hubdle.extensions._internal.isAndroidLibrary
import com.javiersc.hubdle.extensions._internal.isKotlinMultiplatformWithAndroid
import com.javiersc.hubdle.extensions._internal.state.checkCompatibility
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestReport
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.gradle.language.base.plugins.LifecycleBasePlugin

@HubdleDslMarker
public fun Project.hubdle(action: Action<HubdleExtension> = Action {}) {
    action.invoke(the())

    checkCompatibility()
    hubdleState.configure(this)

    configureTestLogger(this)
    configureAllTestsTask(this)
    configureAllTestsReport(this)
    configureCodeCoverageMergedReport(this)
}

private fun configureTestLogger(target: Project) {
    target.pluginManager.apply(PluginIds.Testing.logger)

    target.tasks.withType<Test> {
        testLogging.showStandardStreams = true
        maxParallelForks = (Runtime.getRuntime().availableProcessors() - 2).takeIf { it > 0 } ?: 1

        val hasAndroid =
            project.run {
                isAndroidApplication || isAndroidLibrary || isKotlinMultiplatformWithAndroid
            }

        if (hasAndroid) useJUnit() else useJUnitPlatform()
    }
}

private fun configureAllTestsTask(target: Project) {
    target.tasks
        .maybeRegisterLazily<Task>(ALL_TEST_TASK_NAME) {
            group = LifecycleBasePlugin.VERIFICATION_GROUP
        }
        .configureEach { dependsOn(target.tasks.withType<Test>()) }

    target.tasks.configureEach {
        if (name == LifecycleBasePlugin.CHECK_TASK_NAME) dependsOn(ALL_TEST_TASK_NAME)
    }
}

private fun configureAllTestsReport(target: Project) {
    val testReport = target.tasks.maybeRegisterLazily<TestReport>(ALL_TEST_REPORT_TASK_NAME)
    testReport.configureEach {
        group = LifecycleBasePlugin.VERIFICATION_GROUP
        destinationDirectory.set(project.file("${project.buildDir}/reports/allTests"))
        testResults.from(project.allprojects.map { it.tasks.withType<Test>() })
    }

    val shouldRunAllTestsReport =
        target.gradle.startParameter.taskNames.any { taskName ->
            taskName in
                listOf(
                    ALL_TEST_TASK_NAME,
                    LifecycleBasePlugin.BUILD_TASK_NAME,
                    LifecycleBasePlugin.CHECK_TASK_NAME
                )
        }

    if (shouldRunAllTestsReport) {
        target.allprojects {
            project.tasks.withType<Test>().configureEach { finalizedBy(testReport) }
        }
    }
}

private fun configureCodeCoverageMergedReport(project: Project) {
    if (project.hubdleState.config.coverage.isEnabled) {
        project.afterEvaluate {
            val shouldMergeCodeCoverageReports =
                project.gradle.startParameter.taskNames.any { taskName ->
                    taskName in
                        listOf(
                            ALL_TEST_TASK_NAME,
                            LifecycleBasePlugin.BUILD_TASK_NAME,
                            LifecycleBasePlugin.CHECK_TASK_NAME
                        )
                } &&
                    project.rootProject.tasks.names.contains(KOVER_MERGED_REPORT_TASK_NAME) &&
                    project.rootProject.tasks.names.contains(ALL_TEST_TASK_NAME)

            if (shouldMergeCodeCoverageReports) {
                val koverMergedReportTask =
                    project.rootProject.tasks.namedLazily<Task>(KOVER_MERGED_REPORT_TASK_NAME)
                project.rootProject.tasks.namedLazily<Task>(ALL_TEST_TASK_NAME).configureEach {
                    dependsOn(koverMergedReportTask)
                }
            }
        }
    }
}

private const val ALL_TEST_TASK_NAME = "allTests"
private const val ALL_TEST_REPORT_TASK_NAME = "allTestsReport"
private const val KOVER_MERGED_REPORT_TASK_NAME = "koverMergedReport"
