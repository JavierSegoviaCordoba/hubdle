package com.javiersc.gradle.plugins.all.projects.install.pre.commit

import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register

@CacheableTask
abstract class InstallSpotlessCheckPreCommitTask : InstallPreCommitTask() {

    @get:Input
    override val preCommitName: String
        get() = "spotlessCheck"

    @get:Input abstract val spotlessCheck: Property<Boolean>

    @TaskAction
    override fun install() {
        if (spotlessCheck.get()) {
            createInstallPreCommitGradleTask()
        }
    }

    companion object {
        const val name: String = "installSpotlessCheckPreCommit"

        internal fun register(
            project: Project,
            configure: InstallSpotlessCheckPreCommitTask.() -> Unit
        ) {
            val installApiCheckPreCommitTask =
                project.tasks.register<InstallSpotlessCheckPreCommitTask>(name) {
                    group = taskGroup
                    configure(this)
                    finalizedBy(WriteFilePreCommitTask.getTask(project))
                }
            getInstallPreCommitTask(project).get().dependsOn(installApiCheckPreCommitTask)
        }
    }
}
