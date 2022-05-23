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
abstract class InstallAllTestsPreCommitTask
@Inject
constructor(
    objects: ObjectFactory,
    allProjectsExtension: AllProjectsExtension,
) : InstallPreCommitTask("allTests") {

    @Input
    val allTests: Property<Boolean> =
        objects.convention(
            allProjectsExtension.install
                .flatMap(InstallOptions::preCommit)
                .flatMap(PreCommitOptions::allTests)
        )

    @TaskAction
    override fun install() {
        if (allTests.get()) createInstallPreCommitGradleTask()
    }

    companion object {
        const val name: String = "installAllTestsPreCommit"

        internal fun register(project: Project) {

            val installAllTestsPreCommitTask =
                project.tasks.register<InstallAllTestsPreCommitTask>(
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
