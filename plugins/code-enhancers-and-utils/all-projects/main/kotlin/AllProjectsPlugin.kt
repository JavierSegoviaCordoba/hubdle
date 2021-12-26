package com.javiersc.gradle.plugins.all.projects

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestReport
import org.gradle.language.base.plugins.LifecycleBasePlugin

abstract class AllProjectsPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply(LifecycleBasePlugin::class.java)

        target.allprojects { project ->
            project.group = project.module

            project.configureTestLogger()
            project.configureAllTestsReport()
            project.configureAllTestsTask()
        }
    }
}

private fun Project.configureTestLogger() {
    pluginManager.apply("com.adarshr.test-logger")

    tasks.withType(Test::class.java) { test ->
        test.testLogging.showStandardStreams = true
        test.maxParallelForks =
            (Runtime.getRuntime().availableProcessors() / 3).takeIf { it > 0 } ?: 1
        test.useJUnitPlatform()
    }
}

private fun Project.configureAllTestsTask() {
    afterEvaluate { project ->
        if (project.tasks.findByName("allTests") == null) {
            project.tasks.register("allTests") { task ->
                task.group = "verification"
                task.dependsOn(project.tasks.withType(Test::class.java))
            }
        } else {
            project.tasks.named("allTests") { task ->
                task.dependsOn(project.tasks.withType(Test::class.java))
            }
        }
    }
}

private fun Project.configureAllTestsReport() {
    val testReport =
        tasks.register("allTestsReport", TestReport::class.java) { testReport ->
            testReport.group = "verification"
            testReport.destinationDir = file("$buildDir/reports/allTests")
            testReport.reportOn(allprojects.map { it.tasks.withType(Test::class.java) })
        }

    allprojects { project ->
        project.tasks.withType(Test::class.java) { test -> test.finalizedBy(testReport) }
    }
}
