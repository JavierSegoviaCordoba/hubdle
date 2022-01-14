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
import org.gradle.language.base.plugins.LifecycleBasePlugin
import org.gradle.language.base.plugins.LifecycleBasePlugin.BUILD_TASK_NAME
import org.gradle.language.base.plugins.LifecycleBasePlugin.CHECK_TASK_NAME
import org.gradle.language.base.plugins.LifecycleBasePlugin.VERIFICATION_GROUP

abstract class AllProjectsPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        check(target == target.rootProject) { "`all-projects` must be applied in the root project" }

        target.pluginManager.apply(LifecycleBasePlugin::class.java)

        AllProjectsExtension.createExtension(target)

        InstallPreCommitTask.register(target)
        InstallAllTestsPreCommitTask.register(target)
        InstallAssemblePreCommitTask.register(target)
        InstallApiCheckPreCommitTask.register(target)
        InstallSpotlessCheckPreCommitTask.register(target)
        WriteFilePreCommitTask.register(target)

        target.allprojects { project ->
            project.group = project.module

            project.configureTestLogger()
            project.configureAllTestsTask()
            project.configureAllTestsReport()
        }

        target.configureCodeCoverageMergedReport()
    }
}

private fun Project.configureTestLogger() {
    pluginManager.apply("com.adarshr.test-logger")

    tasks.withType(Test::class.java) { test ->
        test.testLogging.showStandardStreams = true
        test.maxParallelForks =
            (Runtime.getRuntime().availableProcessors() / 3).takeIf { it > 0 } ?: 1

        val hasAndroid =
            test.project.run {
                isAndroidApplication || isAndroidLibrary || isKotlinMultiplatformWithAndroid
            }

        if (hasAndroid) test.useJUnit() else test.useJUnitPlatform()
    }
}

private fun Project.configureAllTestsTask() {
    afterEvaluate { project ->
        val checkTask = project.tasks.findByName(CHECK_TASK_NAME)

        if (project.tasks.findByName(AllTestsLabel) == null) {
            project.tasks.register(AllTestsLabel) { task ->
                task.group = VERIFICATION_GROUP
                task.dependsOn(project.tasks.withType(Test::class.java))
            }
        } else {
            project.tasks.named(AllTestsLabel) { allTestsTask ->
                allTestsTask.dependsOn(project.tasks.withType(Test::class.java))
            }
        }
        checkTask?.dependsOn(AllTestsLabel)
    }
}

private fun Project.configureAllTestsReport() {
    val testReport =
        tasks.register(AllTestsReportLabel, TestReport::class.java) { testReport ->
            testReport.group = VERIFICATION_GROUP
            testReport.destinationDir = file("$buildDir/reports/allTests")
            testReport.reportOn(allprojects.map { it.tasks.withType(Test::class.java) })
        }

    val shouldRunAllTestsReport =
        gradle.startParameter.taskNames.any { taskName ->
            taskName in listOf(AllTestsLabel, BUILD_TASK_NAME, CHECK_TASK_NAME)
        }

    if (shouldRunAllTestsReport) {
        allprojects { project ->
            project.tasks.withType(Test::class.java) { test -> test.finalizedBy(testReport) }
        }
    }
}

private fun Project.configureCodeCoverageMergedReport() {
    afterEvaluate {
        val shouldMergeCodeCoverageReports =
            gradle.startParameter.taskNames.any { taskName ->
                taskName in listOf(AllTestsLabel, BUILD_TASK_NAME, CHECK_TASK_NAME)
            }

        if (shouldMergeCodeCoverageReports) {
            val koverMergedReportTask = rootProject.tasks.findByName(KoverMergedReport)
            val allTestsTask = rootProject.tasks.findByName(AllTestsLabel)

            if (allTestsTask != null && koverMergedReportTask != null) {
                allTestsTask.dependsOn(koverMergedReportTask)
            }
        }
    }
}

private const val AllTestsLabel = "allTests"
private const val AllTestsReportLabel = "allTestsReport"
private const val KoverMergedReport = "koverMergedReport"
