package com.javiersc.hubdle.project.extensions.config.install.pre.commits.tasks

import java.io.File
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.ProjectLayout
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register

@CacheableTask
public abstract class WriteFilePreCommitTask
@Inject
constructor(
    layout: ProjectLayout,
) : DefaultTask() {

    init {
        group = "install"
    }

    private val installPreCommitsDir: File =
        layout.buildDirectory.asFile.get().resolve("install/pre-commits")

    private val preCommitFile: File = layout.preCommitFile

    @TaskAction
    public fun writeFile() {
        installPreCommitsDir.mkdirs()
        preCommitFile.apply {
            parentFile.mkdirs()
            createNewFile()
        }
        val currentPreCommitText = preCommitFile.readText()

        val preCommitText: String =
            installPreCommitsDir
                .listFiles()
                ?.map(File::readText)
                ?.reduce { accumulative, text ->
                    if (!currentPreCommitText.contains(text)) accumulative + text else accumulative
                }
                .orEmpty()
        if (currentPreCommitText.lines().firstOrNull()?.contains("#!/bin/bash") == true) {
            preCommitFile.writeText(currentPreCommitText + preCommitText)
        } else {
            preCommitFile.writeText("#!/bin/bash\n$currentPreCommitText$preCommitText")
        }
    }

    public companion object {
        public const val name: String = "writeFilePreCommit"

        internal fun register(project: Project) {
            project.tasks.register<WriteFilePreCommitTask>(name)
        }

        internal fun getTask(project: Project): TaskProvider<WriteFilePreCommitTask> =
            project.tasks.named<WriteFilePreCommitTask>(name)
    }
}
