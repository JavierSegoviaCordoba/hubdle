package com.javiersc.gradle.plugins.all.projects.install.pre.commit

import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register

@CacheableTask
abstract class InstallApiCheckPreCommitTask : InstallPreCommitTask() {

    @get:Input
    override val preCommitName: String
        get() = "apiCheck"

    @get:Input abstract val apiCheck: Property<Boolean>

    @TaskAction
    override fun install() {
        if (apiCheck.get()) {
            createInstallPreCommitGradleTask()
        }
    }

    companion object {
        const val name: String = "installApiCheckPreCommit"

        internal fun register(
            project: Project,
            configure: InstallApiCheckPreCommitTask.() -> Unit
        ) {
            val installApiCheckPreCommitTask =
                project.tasks.register<InstallApiCheckPreCommitTask>(name) {
                    group = taskGroup
                    configure(this)
                    finalizedBy(WriteFilePreCommitTask.getTask(project))
                }
            getInstallPreCommitTask(project).get().dependsOn(installApiCheckPreCommitTask)
        }
    }
}
