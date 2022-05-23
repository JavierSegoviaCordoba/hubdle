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
abstract class InstallApiCheckPreCommitTask
@Inject
constructor(
    objects: ObjectFactory,
    allProjectsExtension: AllProjectsExtension,
) : InstallPreCommitTask("apiCheck") {

    @Input
    val apiCheck: Property<Boolean> =
        objects.convention(
            allProjectsExtension.install
                .flatMap(InstallOptions::preCommit)
                .flatMap(PreCommitOptions::apiCheck)
        )

    @TaskAction
    override fun install() {
        if (apiCheck.get()) {
            createInstallPreCommitGradleTask()
        }
    }

    companion object {
        const val name: String = "installApiCheckPreCommit"

        internal fun register(project: Project) {
            val installApiCheckPreCommitTask =
                project.tasks.register<InstallApiCheckPreCommitTask>(
                    name,
                    project.allProjectsExtension
                )

            installApiCheckPreCommitTask.configure { task ->
                task.finalizedBy(WriteFilePreCommitTask.getTask(project))
            }

            getInstallPreCommitTask(project).get().dependsOn(installApiCheckPreCommitTask)
        }
    }
}
