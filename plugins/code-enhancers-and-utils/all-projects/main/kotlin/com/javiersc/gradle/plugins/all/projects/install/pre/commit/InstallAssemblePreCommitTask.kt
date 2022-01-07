package com.javiersc.gradle.plugins.all.projects.install.pre.commit

import com.javiersc.gradle.plugins.all.projects.allProjectsExtension
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class InstallAssemblePreCommitTask : InstallPreCommitTask() {

    @get:Input
    override val preCommitName: String
        get() = "assemble"

    @TaskAction
    override fun install() {
        if (project.allProjectsExtension.install.get().preCommit.get().assemble.get()) {
            createInstallPreCommitGradleTask()
        }
    }

    companion object {
        const val name: String = "installAssemblePreCommit"

        internal fun register(project: Project) {
            val installAllTestsPreCommitTask =
                project.tasks.register(name, InstallAssemblePreCommitTask::class.java) { task ->
                    task.group = taskGroup

                    task.finalizedBy(WriteFilePreCommitTask.getTask(project))
                }
            getInstallPreCommitTask(project).get().dependsOn(installAllTestsPreCommitTask)
        }
    }
}
