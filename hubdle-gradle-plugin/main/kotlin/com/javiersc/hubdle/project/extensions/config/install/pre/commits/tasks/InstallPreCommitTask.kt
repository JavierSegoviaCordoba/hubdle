package com.javiersc.hubdle.project.extensions.config.install.pre.commits.tasks

import com.javiersc.gradle.logging.extensions.debugColored
import com.javiersc.hubdle.project.extensions.config.install.InstallTask
import java.io.File
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.ProjectLayout
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register

@CacheableTask
public abstract class InstallPreCommitTask @Inject constructor(layout: ProjectLayout) :
    InstallTask, DefaultTask() {

    init {
        group = "install"
    }

    @get:Input public abstract val preCommitName: Property<String>

    @OutputDirectory
    public val preCommitOutputDir: File =
        layout.buildDirectory.asFile.get().resolve("install/pre-commits/")

    @get:OutputFile
    public val preCommitOutputFile: File
        get() = preCommitOutputDir.resolve("${preCommitName.get()}.pre-commit")

    @TaskAction
    override fun install() {
        logger.debugColored { "Install pre-commit task" }
    }

    public companion object {
        public const val NAME: String = "installPreCommit"

        internal fun register(project: Project) =
            project.tasks.register<InstallPreCommitTask>(NAME).configure { task ->
                task.preCommitName.set("")
            }
    }
}

internal fun InstallPreCommitTask.createInstallPreCommitGradleTask() {
    val headerText = "[${preCommitName.get()} autogenerated by `Hubdle` plugin]"
    val text =
        """
            |
            |# $headerText
            |./gradlew ${preCommitName.get()}
            |
        """
            .trimMargin()

    preCommitOutputFile.writeText(text)
}

internal val ProjectLayout.preCommitFile: File
    get() = projectDirectory.file(".git/hooks/pre-commit").asFile