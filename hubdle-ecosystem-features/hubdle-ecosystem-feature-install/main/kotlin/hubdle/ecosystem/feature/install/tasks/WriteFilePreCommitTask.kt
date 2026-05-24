package hubdle.ecosystem.feature.install.tasks

import java.io.File
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.ProjectLayout
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register

@CacheableTask
public abstract class WriteFilePreCommitTask
@Inject
constructor(layout: ProjectLayout, objects: ObjectFactory) : DefaultTask() {

    init {
        group = "install"
    }

    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    public val installPreCommitsDir: DirectoryProperty =
        objects.directoryProperty().convention(layout.preCommitHubdleDir.dir("pre-commits"))

    @get:OutputFile
    public val preCommitFile: RegularFileProperty =
        objects.fileProperty().convention { layout.preCommitFile }

    @get:OutputFile
    public val preCommitHubdleFile: RegularFileProperty =
        objects.fileProperty().convention { layout.preCommitHubdleFile }

    @TaskAction
    public fun writeFile() {
        val installPreCommitsDir: File = installPreCommitsDir.asFile.get().apply(File::mkdirs)
        val preCommitHubdleFile: File =
            preCommitHubdleFile.asFile.get().apply {
                parentFile.mkdirs()
                createNewFile()
            }
        val preCommitFile: File = preCommitFile.asFile.get()

        val preCommitHubdleText: String =
            installPreCommitsDir
                .listFiles()
                ?.sortedBy { it.name }
                ?.joinToString(separator = "", transform = File::readText)
                .orEmpty()
        preCommitHubdleFile.writeText("#!/bin/bash\n$preCommitHubdleText")
        preCommitHubdleFile.setExecutable(true)

        if (preCommitFile.exists()) {
            val currentPreCommitText = preCommitFile.readText()
            if (!currentPreCommitText.contains(".git/hooks/.hubdle/pre-commit")) {
                logger.warn(
                    """
                    Existing '.git/hooks/pre-commit' does not load Hubdle hook.
                    Add this snippet manually:
                    if [ -f .git/hooks/.hubdle/pre-commit ]; then
                      .git/hooks/.hubdle/pre-commit
                    fi
                    """
                        .trimIndent()
                )
            }
        } else {
            preCommitFile.parentFile.mkdirs()
            preCommitFile.writeText(
                """
                #!/bin/bash
                if [ -f .git/hooks/.hubdle/pre-commit ]; then
                  .git/hooks/.hubdle/pre-commit
                fi
                """
                    .trimIndent()
                    .plus("\n")
            )
            preCommitFile.setExecutable(true)
        }
    }

    public companion object {
        public const val NAME: String = "writeFilePreCommit"

        internal fun register(project: Project) {
            val task = project.tasks.register<WriteFilePreCommitTask>(NAME)
            task.configure { it.dependsOn(project.tasks.named(InstallPreCommitTask.NAME)) }
        }

        internal fun getTask(project: Project): TaskProvider<WriteFilePreCommitTask> =
            project.tasks.named<WriteFilePreCommitTask>(NAME)
    }
}
