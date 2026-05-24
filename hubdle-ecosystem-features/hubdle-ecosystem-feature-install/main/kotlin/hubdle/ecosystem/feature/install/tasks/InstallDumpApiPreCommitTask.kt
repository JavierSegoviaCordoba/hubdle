package hubdle.ecosystem.feature.install.tasks

import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register

@CacheableTask
public abstract class InstallDumpApiPreCommitTask
@Inject
constructor(objects: ObjectFactory, layout: ProjectLayout) : InstallPreCommitTask(layout, objects) {

    @TaskAction override fun install(): Unit = createInstallPreCommitGradleTask()

    public companion object {
        public const val NAME: String = "installDumpApiPreCommit"

        internal fun register(project: Project) {
            val task = project.tasks.register<InstallDumpApiPreCommitTask>(NAME)
            task.configure {
                it.preCommitName.convention("dumpApi")
                it.finalizedBy(WriteFilePreCommitTask.getTask(project))
            }
            project.tasks.named<InstallPreCommitTask>(InstallPreCommitTask.NAME).configure {
                it.dependsOn(task)
            }
        }
    }
}
