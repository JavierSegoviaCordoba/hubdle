package com.javiersc.hubdle.extensions.config.install.commit

import com.javiersc.gradle.extensions.namedLazily
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.file.ProjectLayout
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register

@CacheableTask
public abstract class InstallCheckAnalysisPreCommitTask
@Inject
constructor(
    layout: ProjectLayout,
) : InstallPreCommitTask(layout) {

    init {
        group = "install"
    }

    override val preCommitName: String = "checkAnalysis"

    @TaskAction
    public override fun install() {
        createInstallPreCommitGradleTask()
    }

    public companion object {
        public const val name: String = "installCheckAnalysisPreCommit"

        internal fun register(project: Project) {
            val installCheckAnalysisPreCommitTask =
                project.tasks.register<InstallCheckAnalysisPreCommitTask>(name)

            installCheckAnalysisPreCommitTask.configure { task ->
                task.finalizedBy(WriteFilePreCommitTask.getTask(project))
            }

            project.tasks
                .namedLazily<InstallPreCommitTask>(InstallPreCommitTask.name)
                .configureEach { it.dependsOn(installCheckAnalysisPreCommitTask) }
        }
    }
}
