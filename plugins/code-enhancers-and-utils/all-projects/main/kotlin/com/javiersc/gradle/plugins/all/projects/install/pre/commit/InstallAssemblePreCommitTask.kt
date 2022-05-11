package com.javiersc.gradle.plugins.all.projects.install.pre.commit

import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register

@CacheableTask
abstract class InstallAssemblePreCommitTask : InstallPreCommitTask() {

    @get:Input
    override val preCommitName: String
        get() = "assemble"

    @get:Input abstract val assemble: Property<Boolean>

    @TaskAction
    override fun install() {
        if (assemble.get()) {
            createInstallPreCommitGradleTask()
        }
    }

    companion object {
        const val name: String = "installAssemblePreCommit"

        internal fun register(
            project: Project,
            configure: InstallAssemblePreCommitTask.() -> Unit
        ) {
            val installAllTestsPreCommitTask =
                project.tasks.register<InstallAssemblePreCommitTask>(name) {
                    group = taskGroup
                    configure(this)
                    finalizedBy(WriteFilePreCommitTask.getTask(project))
                }
            getInstallPreCommitTask(project).get().dependsOn(installAllTestsPreCommitTask)
        }
    }
}
