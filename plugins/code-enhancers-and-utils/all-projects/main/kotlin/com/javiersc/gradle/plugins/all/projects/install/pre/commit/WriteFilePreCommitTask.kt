package com.javiersc.gradle.plugins.all.projects.install.pre.commit

import java.io.File
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register

@CacheableTask
abstract class WriteFilePreCommitTask @Inject constructor(project: Project) : DefaultTask() {

    @Internal
    val installPreCommitsDir: File =
        project.file("${project.rootProject.buildDir}/install/pre-commits/").apply { mkdirs() }

    @InputFile
    @PathSensitive(PathSensitivity.ABSOLUTE)
    val inputPreCommitFile: File = project.preCommitFile

    @OutputFile val outputPreCommitFile: File = project.preCommitFile

    @TaskAction
    fun writeFile() {
        val currentPreCommitText = inputPreCommitFile.apply { createNewFile() }.readText()

        val preCommitText: String =
            installPreCommitsDir
                .listFiles()
                ?.map(File::readText)
                ?.reduce { accumulative, text ->
                    if (!currentPreCommitText.contains(text)) accumulative + text else accumulative
                }
                .orEmpty()
        if (currentPreCommitText.lines().firstOrNull()?.contains("#!/bin/bash") == true) {
            outputPreCommitFile.writeText(currentPreCommitText + preCommitText)
        } else {
            outputPreCommitFile.writeText("#!/bin/bash\n$currentPreCommitText$preCommitText")
        }
    }

    companion object {
        const val name: String = "writeFilePreCommit"

        internal fun register(project: Project) {
            project.tasks.register<WriteFilePreCommitTask>(name)
        }

        internal fun getTask(project: Project): TaskProvider<WriteFilePreCommitTask> =
            project.tasks.named<WriteFilePreCommitTask>(name)
    }
}
