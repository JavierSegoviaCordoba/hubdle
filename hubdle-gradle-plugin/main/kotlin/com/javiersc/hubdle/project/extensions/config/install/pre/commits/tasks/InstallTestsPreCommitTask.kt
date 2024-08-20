package com.javiersc.hubdle.project.extensions.config.install.pre.commits.tasks

import com.javiersc.hubdle.project.tasks.lifecycle.TestsTask
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register

@CacheableTask
public abstract class InstallTestsPreCommitTask
@Inject
constructor(objects: ObjectFactory, layout: ProjectLayout) : InstallPreCommitTask(layout) {

    init {
        group = "install"
    }

    @Input
    override val preCommitName: Property<String> =
        objects.property<String>().convention(TestsTask.NAME)

    @TaskAction
    override fun install() {
        createInstallPreCommitGradleTask()
    }

    public companion object {
        public const val NAME: String = "installTestsPreCommit"

        internal fun register(project: Project) {

            val installTestsPreCommitTask = project.tasks.register<InstallTestsPreCommitTask>(NAME)

            installTestsPreCommitTask.configure { task ->
                task.finalizedBy(WriteFilePreCommitTask.getTask(project))
            }

            project.tasks.named<InstallPreCommitTask>(InstallPreCommitTask.NAME).configure { task ->
                task.dependsOn(installTestsPreCommitTask)
            }
        }
    }
}
