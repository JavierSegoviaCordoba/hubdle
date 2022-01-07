package com.javiersc.gradle.plugins.all.projects.install.pre.commit

import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider

open class WriteFilePreCommitTask : DefaultTask() {

    @get:InputDirectory
    val installPreCommitsDir: File =
        project.file("${project.rootProject.buildDir}/install/pre-commits/")

    @get:OutputFile val preCommitFile: File = project.preCommitFile

    @TaskAction
    fun writeFile() {
        val currentPreCommitText = preCommitFile.readText()

        val preCommitText: String =
            installPreCommitsDir
                .listFiles()
                ?.map(File::readText)
                ?.reduce { accumulative, text ->
                    if (!currentPreCommitText.contains(text)) accumulative + text else accumulative
                }
                .orEmpty()
        preCommitFile.writeText(currentPreCommitText + preCommitText)
    }

    companion object {
        const val name: String = "writeFilePreCommit"

        internal fun register(project: Project) {
            project.tasks.register(name, WriteFilePreCommitTask::class.java) { task ->
                task.group = InstallPreCommitTask.taskGroup
            }
        }

        internal fun getTask(project: Project): TaskProvider<WriteFilePreCommitTask> =
            project.tasks.named(name, WriteFilePreCommitTask::class.java)
    }
}
