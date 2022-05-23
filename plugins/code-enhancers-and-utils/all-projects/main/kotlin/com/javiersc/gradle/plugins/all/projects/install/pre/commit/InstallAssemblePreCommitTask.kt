package com.javiersc.gradle.plugins.all.projects.install.pre.commit

import com.javiersc.gradle.plugins.all.projects.AllProjectsExtension
import com.javiersc.gradle.plugins.all.projects.allProjectsExtension
import com.javiersc.gradle.plugins.all.projects.install.InstallOptions
import com.javiersc.gradle.plugins.core.convention
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register

@CacheableTask
abstract class InstallAssemblePreCommitTask
@Inject
constructor(
    objects: ObjectFactory,
    allProjectsExtension: AllProjectsExtension,
) : InstallPreCommitTask("assemble") {

    @Input
    val assemble: Property<Boolean> =
        objects.convention(
            allProjectsExtension.install
                .flatMap(InstallOptions::preCommit)
                .flatMap(PreCommitOptions::assemble)
        )

    @TaskAction
    override fun install() {
        if (assemble.get()) {
            createInstallPreCommitGradleTask()
        }
    }

    companion object {
        const val name: String = "installAssemblePreCommit"

        internal fun register(project: Project) {
            val installAllTestsPreCommitTask =
                project.tasks.register<InstallAssemblePreCommitTask>(
                    name,
                    project.allProjectsExtension
                )
            installAllTestsPreCommitTask.configure { task ->
                task.finalizedBy(WriteFilePreCommitTask.getTask(project))
            }
            getInstallPreCommitTask(project).get().dependsOn(installAllTestsPreCommitTask)
        }
    }
}
