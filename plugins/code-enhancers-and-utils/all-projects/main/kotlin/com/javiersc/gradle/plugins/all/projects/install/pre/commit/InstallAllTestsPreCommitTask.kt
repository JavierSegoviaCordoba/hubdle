package com.javiersc.gradle.plugins.all.projects.install.pre.commit

import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register

@CacheableTask
abstract class InstallAllTestsPreCommitTask : InstallPreCommitTask() {

    @get:Input
    override val preCommitName: String
        get() = "allTests"

    @get:Input abstract val allTests: Property<Boolean>

    @TaskAction
    override fun install() {
        if (allTests.get()) createInstallPreCommitGradleTask()
    }

    companion object {
        const val name: String = "installAllTestsPreCommit"

        internal fun register(
            project: Project,
            configure: InstallAllTestsPreCommitTask.() -> Unit
        ) {
            val installAllTestsPreCommitTask =
                project.tasks.register<InstallAllTestsPreCommitTask>(name) {
                    group = taskGroup
                    configure(this)
                    finalizedBy(WriteFilePreCommitTask.getTask(project))
                }
            getInstallPreCommitTask(project).get().dependsOn(installAllTestsPreCommitTask)
        }
    }
}
