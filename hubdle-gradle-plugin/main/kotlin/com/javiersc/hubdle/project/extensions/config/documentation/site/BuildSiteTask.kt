package com.javiersc.hubdle.project.extensions.config.documentation.site

import com.android.build.gradle.internal.tasks.factory.dependsOn
import com.javiersc.gradle.project.extensions.withPlugins
import com.javiersc.hubdle.project.extensions.config.documentation.api.hubdleApi
import com.javiersc.hubdle.project.extensions.shared.PluginId
import java.io.File
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.Task
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
            preBuildSiteTask: TaskProvider<PreBuildSiteTask>,
        ): TaskProvider<BuildSiteTask> {
            val buildSiteTask = project.tasks.register<BuildSiteTask>(name)
            buildSiteTask.configure { task -> task.inputs.files(preBuildSiteTask) }

            val isDokkaEnabled = project.hubdleApi.isFullEnabled.get()
            val isMkdocsEnabled = project.hubdleSite.isFullEnabled.get()

            fun getDokkaTask(): TaskProvider<Task> = project.tasks.named("dokkaHtmlMultiModule")
            fun getMkdocsBuildTask(): TaskProvider<Task> = project.tasks.named("mkdocsBuild")

            project.pluginManager.withPlugin(PluginId.JetbrainsDokka.id) {
                if (isDokkaEnabled) {
                    val dokkaHtmlMultiModuleTask: TaskProvider<Task> = getDokkaTask()
                    buildSiteTask.dependsOn(dokkaHtmlMultiModuleTask)
                }
            }
            project.pluginManager.withPlugin(PluginId.VyarusMkdocsBuild.id) {
                if (isMkdocsEnabled) {
                    val mkdocsBuildTask: TaskProvider<Task> = getMkdocsBuildTask()
                    mkdocsBuildTask.configure { task -> task.dependsOn(preBuildSiteTask) }
                    buildSiteTask.dependsOn(mkdocsBuildTask)
                }
            }

            project.withPlugins(PluginId.JetbrainsDokka.id, PluginId.VyarusMkdocsBuild.id) {
                if (isDokkaEnabled && isMkdocsEnabled) {
                    val dokkaHtmlMultiModuleTask: TaskProvider<Task> = getDokkaTask()
                    val mkdocsBuildTask: TaskProvider<Task> = getMkdocsBuildTask()
                    dokkaHtmlMultiModuleTask.configure { task -> task.dependsOn(mkdocsBuildTask) }
                }
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
