package com.javiersc.gradle.plugins.all.projects.install.pre.commit

import com.javiersc.gradle.plugins.all.projects.allProjectsExtension
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class InstallApiCheckPreCommitTask : InstallPreCommitTask() {

    @get:Input
    override val preCommitName: String
        get() = "apiCheck"

    @TaskAction
    override fun install() {
        if (project.allProjectsExtension.install.get().preCommit.get().apiCheck.get()) {
            createInstallPreCommitGradleTask()
        }
    }

    companion object {
        const val name: String = "installApiCheckPreCommit"

        internal fun register(project: Project) {
            val installApiCheckPreCommitTask =
                project.tasks.register(name, InstallApiCheckPreCommitTask::class.java) { task ->
                    task.group = taskGroup

                    task.finalizedBy(WriteFilePreCommitTask.getTask(project))
                }
            getInstallPreCommitTask(project).get().dependsOn(installApiCheckPreCommitTask)
        }
    }
}
