package com.javiersc.hubdle.project.extensions.config.documentation.site

import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.javiersc.hubdle.project.extensions.config.documentation.api.hubdleApi
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

public abstract class BuildSiteTask
@Inject
constructor(
    private val layout: ProjectLayout,
    private val fileSystemOperations: FileSystemOperations,
) : DefaultTask() {

    init {
        group = "documentation"
    }

    @get:Input
    public val projectVersion: String
        get() = project.version.toString()

    @get:OutputDirectory
    public val dokkaOutputDir: File
        get() = File("${layout.projectDirectory.asFile}/build/dokka/htmlMultiModule/")

    @get:OutputDirectory
    public val apiDir: File
        get() = File("${layout.projectDirectory.asFile}/build/docs/_site/api/").apply(File::mkdirs)

    @TaskAction
    public fun buildDocs() {
        moveApiDocsInToDocs()
    }

    public companion object {
        public const val name: String = "buildSite"

        public fun register(
            project: Project,
            preBuildSiteTask: TaskProvider<PreBuildSiteTask>
        ): TaskProvider<BuildSiteTask> {
            val buildSiteTask = project.tasks.register<BuildSiteTask>(name)
            buildSiteTask.configure { task -> task.inputs.files(preBuildSiteTask) }

            val isDokkaEnabled = project.hubdleApi.isFullEnabled.get()
            val isMkdocsEnabled = project.hubdleSite.isFullEnabled.get()

            fun getDokkaTask() = project.tasks.named("dokkaHtmlMultiModule")
            fun getMkdocsBuildTask() = project.tasks.named("mkdocsBuild")

            if (isDokkaEnabled) {
                val dokkaHtmlMultiModuleTask = getDokkaTask()
                buildSiteTask.dependsOn(dokkaHtmlMultiModuleTask)
            }

            if (isMkdocsEnabled) {
                val mkdocsBuildTask = getMkdocsBuildTask()
                mkdocsBuildTask.configure { task -> task.dependsOn(preBuildSiteTask) }
                buildSiteTask.dependsOn(mkdocsBuildTask)
            }

            if (isDokkaEnabled && isMkdocsEnabled) {
                val dokkaHtmlMultiModuleTask = getDokkaTask()
                val mkdocsBuildTask = getMkdocsBuildTask()
                dokkaHtmlMultiModuleTask.configure { task -> task.dependsOn(mkdocsBuildTask) }
            }

            return buildSiteTask
        }
    }

    private fun moveApiDocsInToDocs() {
        with(fileSystemOperations) {
            if (projectVersion.endsWith("-SNAPSHOT")) {
                copy { copy ->
                    copy.from(dokkaOutputDir.path)
                    copy.into(File("$apiDir/snapshot").path)
                }
            } else {
                File("$apiDir/index.html").apply {
                    createNewFile()
                    writeText(apiIndexHtmlContent)
                }
                copy { copy ->
                    copy.from(dokkaOutputDir.path)
                    copy.into(File("$apiDir/versions/$projectVersion").path)
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
        """
            .trimIndent()
}
