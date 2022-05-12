package com.javiersc.gradle.plugins.docs.tasks

import java.io.File
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.ProjectLayout
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.register

abstract class BuildDocsTask
@Inject
constructor(
    private val layout: ProjectLayout,
    private val fileSystemOperations: FileSystemOperations,
) : DefaultTask() {

    @get:Input
    val projectVersion: String
        get() = project.version.toString()

    @get:OutputDirectory
    val dokkaOutputDir
        get() = File("${layout.projectDirectory.asFile}/build/dokka/htmlMultiModule/")

    @get:OutputDirectory
    val apiDir
        get() = File("${layout.projectDirectory.asFile}/build/docs/_site/api/").apply(File::mkdirs)

    @TaskAction
    fun buildDocs() {
        moveApiDocsInToDocs()
    }

    companion object {
        const val name = "buildDocs"
        const val taskGroup = "documentation"

        fun register(
            project: Project,
            preBuildDocsTask: TaskProvider<PreBuildDocsTask>
        ): TaskProvider<BuildDocsTask> {
            val dokkaHtmlMultiModuleTask = project.tasks.named("dokkaHtmlMultiModule")
            val mkdocsBuildTask =
                project.tasks.named("mkdocsBuild").apply {
                    configure { it.dependsOn(preBuildDocsTask) }
                }

            return project.tasks.register<BuildDocsTask>(name) {
                group = taskGroup

                notCompatibleWithConfigurationCache("mkDocsBuild(grgit) task is incompatible")

                dependsOn(mkdocsBuildTask)
                dependsOn(dokkaHtmlMultiModuleTask)
                dokkaHtmlMultiModuleTask.get().dependsOn(mkdocsBuildTask)
            }
        }
    }

    private fun moveApiDocsInToDocs() {
        with(fileSystemOperations) {
            if (projectVersion.endsWith("-SNAPSHOT")) {
                copy {
                    it.from(dokkaOutputDir.path)
                    it.into(File("$apiDir/snapshot").path)
                }
            } else {
                File("$apiDir/index.html").apply {
                    createNewFile()
                    writeText(apiIndexHtmlContent)
                }
                copy {
                    it.from(dokkaOutputDir.path)
                    it.into(File("$apiDir/versions/$projectVersion").path)
                }
            }
        }
    }

    private val apiIndexHtmlContent: String =
        """
            <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <meta http-equiv="refresh" content="0;URL='versions/$projectVersion'" />
            </head>
            <body>
            </body>
            </html>
        """.trimIndent()
}
