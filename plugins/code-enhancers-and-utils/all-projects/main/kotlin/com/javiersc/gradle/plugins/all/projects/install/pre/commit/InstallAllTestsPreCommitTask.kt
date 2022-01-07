package com.javiersc.gradle.plugins.all.projects.install.pre.commit

import com.javiersc.gradle.plugins.all.projects.allProjectsExtension
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class InstallAllTestsPreCommitTask : InstallPreCommitTask() {

    @get:Input
    override val preCommitName: String
        get() = "allTests"

    @TaskAction
    override fun install() {
        if (project.allProjectsExtension.install.get().preCommit.get().allTests.get()) {
            createInstallPreCommitGradleTask()
        }
    }

    companion object {
        const val name: String = "installAllTestsPreCommit"

        internal fun register(project: Project) {
            val installAllTestsPreCommitTask =
                project.tasks.register(name, InstallAllTestsPreCommitTask::class.java) { task ->
                    task.group = taskGroup

                    task.finalizedBy(WriteFilePreCommitTask.getTask(project))
                }
            getInstallPreCommitTask(project).get().dependsOn(installAllTestsPreCommitTask)
        }
    }
}
