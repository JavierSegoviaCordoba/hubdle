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
        InstallAllTestsPreCommitTask.register(target) {
            allTests.set(project.allProjectsExtension.install.get().preCommit.get().allTests)
        }
        InstallAssemblePreCommitTask.register(target) {
            assemble.set(project.allProjectsExtension.install.get().preCommit.get().assemble)
        }
        InstallApiCheckPreCommitTask.register(target) {
            apiCheck.set(project.allProjectsExtension.install.get().preCommit.get().apiCheck)
        }
        InstallSpotlessCheckPreCommitTask.register(target) {
            spotlessCheck.set(
                project.allProjectsExtension.install.get().preCommit.get().spotlessCheck
            )
        }
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
        maxParallelForks = (Runtime.getRuntime().availableProcessors() / 3).takeIf { it > 0 } ?: 1

        val hasAndroid =
            project.run {
                isAndroidApplication || isAndroidLibrary || isKotlinMultiplatformWithAndroid
            }

        if (hasAndroid) useJUnit() else useJUnitPlatform()
    }
}

private fun configureAllTestsTask(target: Project) {
    target.afterEvaluate { project ->
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

private fun configureAllTestsReport(target: Project) {
    val testReport =
        target.tasks.register<TestReport>(AllTestsReportLabel) {
            group = VERIFICATION_GROUP
            destinationDirectory.set(project.file("${project.buildDir}/reports/allTests"))
            testResults.from(project.allprojects.map { it.tasks.withType(Test::class.java) })
        }

    val shouldRunAllTestsReport =
        target.gradle.startParameter.taskNames.any { taskName ->
            taskName in listOf(AllTestsLabel, BUILD_TASK_NAME, CHECK_TASK_NAME)
        }

    if (shouldRunAllTestsReport) {
        target.allprojects { project -> project.tasks.withType<Test>() { finalizedBy(testReport) } }
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
