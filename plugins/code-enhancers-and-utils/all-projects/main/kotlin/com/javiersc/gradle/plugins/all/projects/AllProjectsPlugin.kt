package com.javiersc.gradle.plugins.all.projects

import com.javiersc.gradle.plugins.all.projects.install.pre.commit.InstallAllTestsPreCommitTask
import com.javiersc.gradle.plugins.all.projects.install.pre.commit.InstallApiCheckPreCommitTask
import com.javiersc.gradle.plugins.all.projects.install.pre.commit.InstallAssemblePreCommitTask
import com.javiersc.gradle.plugins.all.projects.install.pre.commit.InstallPreCommitTask
import com.javiersc.gradle.plugins.all.projects.install.pre.commit.InstallSpotlessCheckPreCommitTask
import com.javiersc.gradle.plugins.all.projects.install.pre.commit.WriteFilePreCommitTask
import com.javiersc.gradle.plugins.core.isAndroidApplication
import com.javiersc.gradle.plugins.core.isAndroidLibrary
import com.javiersc.gradle.plugins.core.isKotlinMultiplatformWithAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestReport
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.language.base.plugins.LifecycleBasePlugin
import org.gradle.language.base.plugins.LifecycleBasePlugin.BUILD_TASK_NAME
import org.gradle.language.base.plugins.LifecycleBasePlugin.CHECK_TASK_NAME
import org.gradle.language.base.plugins.LifecycleBasePlugin.VERIFICATION_GROUP

abstract class AllProjectsPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        check(target == target.rootProject) { "`all-projects` must be applied in the root project" }

        target.pluginManager.apply(LifecycleBasePlugin::class)

        AllProjectsExtension.createExtension(target)

        InstallPreCommitTask.register(target)
        InstallAllTestsPreCommitTask.register(target)
        InstallAssemblePreCommitTask.register(target)
        InstallApiCheckPreCommitTask.register(target)
        InstallSpotlessCheckPreCommitTask.register(target)
        WriteFilePreCommitTask.register(target)

        target.allprojects { project ->
            project.group = project.module

            configureTestLogger(project)
            configureAllTestsTask(project)
            configureAllTestsReport(project)
        }

        target.configureCodeCoverageMergedReport()
    }
}

private fun configureTestLogger(target: Project) {
    target.pluginManager.apply("com.adarshr.test-logger")

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
    target.afterEvaluate { project ->
        if (project.tasks.names.contains(AllTestsLabel)) {
            project.tasks.named(AllTestsLabel).configure { allTestsTask ->
                allTestsTask.dependsOn(project.tasks.withType<Test>())
            }
        } else {
            project.tasks.register(AllTestsLabel).configure { allTestsTask ->
                allTestsTask.group = VERIFICATION_GROUP
                allTestsTask.dependsOn(project.tasks.withType<Test>())
            }
        }
        project.tasks.configureEach { task ->
            if (task.name == CHECK_TASK_NAME) task.dependsOn(AllTestsLabel)
        }
    }
}

private fun configureAllTestsReport(target: Project) {
    val testReport = target.tasks.register<TestReport>(AllTestsReportLabel)
    testReport.configure { task ->
        val project = task.project
        task.group = VERIFICATION_GROUP
        task.destinationDirectory.set(project.file("${project.buildDir}/reports/allTests"))
        task.testResults.from(project.allprojects.map { it.tasks.withType<Test>() })
    }

    val shouldRunAllTestsReport =
        target.gradle.startParameter.taskNames.any { taskName ->
            taskName in listOf(AllTestsLabel, BUILD_TASK_NAME, CHECK_TASK_NAME)
        }

    if (shouldRunAllTestsReport) {
        target.allprojects { project ->
            project.tasks.withType<Test>().configureEach { task -> task.finalizedBy(testReport) }
        }
    }
}

private fun Project.configureCodeCoverageMergedReport() {
    afterEvaluate {
        val shouldMergeCodeCoverageReports =
            gradle.startParameter.taskNames.any { taskName ->
                taskName in listOf(AllTestsLabel, BUILD_TASK_NAME, CHECK_TASK_NAME)
            } &&
                rootProject.tasks.names.contains(KoverMergedReport) &&
                rootProject.tasks.names.contains(AllTestsLabel)

        if (shouldMergeCodeCoverageReports) {
            val koverMergedReportTask = rootProject.tasks.named(KoverMergedReport)
            rootProject.tasks.named(AllTestsLabel).configure { allTestsTask ->
                allTestsTask.dependsOn(koverMergedReportTask)
            }
        }
    }
}

private const val AllTestsLabel = "allTests"
private const val AllTestsReportLabel = "allTestsReport"
private const val KoverMergedReport = "koverMergedReport"
