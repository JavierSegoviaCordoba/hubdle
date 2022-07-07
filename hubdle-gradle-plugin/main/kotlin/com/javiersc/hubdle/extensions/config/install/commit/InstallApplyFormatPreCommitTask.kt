package com.javiersc.hubdle.extensions.config.install.commit

import com.javiersc.gradle.tasks.extensions.namedLazily
import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register

@CacheableTask
public abstract class InstallApplyFormatPreCommitTask
@Inject
constructor(
    objects: ObjectFactory,
    layout: ProjectLayout,
) : InstallPreCommitTask(layout) {

    init {
        group = "install"
    }

    @Input
    override val preCommitName: Property<String> =
        objects.property<String>().convention("applyFormat")

    @TaskAction
    public override fun install() {
        createInstallPreCommitGradleTask()
    }

    public companion object {
        public const val name: String = "installApplyFormatPreCommit"

        internal fun register(project: Project) {
            val applyFormatPreCommitTask =
                project.tasks.register<InstallApplyFormatPreCommitTask>(name)

            applyFormatPreCommitTask.configure {
                finalizedBy(WriteFilePreCommitTask.getTask(project))
            }

            project.tasks
                .namedLazily<InstallPreCommitTask>(InstallPreCommitTask.name)
                .configureEach { dependsOn(applyFormatPreCommitTask) }
        }
    }
}
