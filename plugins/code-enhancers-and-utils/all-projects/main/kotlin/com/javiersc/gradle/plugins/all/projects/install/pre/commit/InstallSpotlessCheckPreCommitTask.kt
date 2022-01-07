package com.javiersc.gradle.plugins.all.projects.install.pre.commit

import com.javiersc.gradle.plugins.all.projects.allProjectsExtension
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class InstallSpotlessCheckPreCommitTask : InstallPreCommitTask() {

    @get:Input
    override val preCommitName: String
        get() = "spotlessCheck"

    @TaskAction
    override fun install() {
        if (project.allProjectsExtension.install.get().preCommit.get().spotlessCheck.get()) {
            createInstallPreCommitGradleTask()
        }
    }

    companion object {
        const val name: String = "installSpotlessCheckPreCommit"

        internal fun register(project: Project) {
            val installApiCheckPreCommitTask =
                project.tasks.register(name, InstallSpotlessCheckPreCommitTask::class.java) { task
                    ->
                    task.group = taskGroup

                    task.finalizedBy(WriteFilePreCommitTask.getTask(project))
                }
            getInstallPreCommitTask(project).get().dependsOn(installApiCheckPreCommitTask)
        }
    }
}
