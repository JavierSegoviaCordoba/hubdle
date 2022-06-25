package com.javiersc.hubdle.extensions.config.install.commit

import com.javiersc.gradle.extensions.namedLazily
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.file.ProjectLayout
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register

@CacheableTask
public abstract class InstallAssemblePreCommitTask
@Inject
constructor(
    layout: ProjectLayout,
) : InstallPreCommitTask(layout) {

    init {
        group = "install"
    }

    @Input override val preCommitName: String = "assemble"

    @TaskAction
    override fun install() {
        createInstallPreCommitGradleTask()
    }

    public companion object {
        public const val name: String = "installAssemblePreCommit"

        internal fun register(project: Project) {
            val installAssemblePreCommitTask =
                project.tasks.register<InstallAssemblePreCommitTask>(name)

            installAssemblePreCommitTask.configure { task ->
                task.finalizedBy(WriteFilePreCommitTask.getTask(project))
            }

            project.tasks
                .namedLazily<InstallPreCommitTask>(InstallPreCommitTask.name)
                .configureEach { it.dependsOn(installAssemblePreCommitTask) }
        }
    }
}
