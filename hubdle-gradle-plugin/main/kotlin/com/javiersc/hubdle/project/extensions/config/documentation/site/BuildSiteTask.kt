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
import org.gradle.api.file.Directory
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register

@CacheableTask
public abstract class BuildSiteTask
@Inject
constructor(
    layout: ProjectLayout,
    objects: ObjectFactory,
    private val fileSystemOperations: FileSystemOperations,
) : DefaultTask() {

    init {
        group = "documentation"
    }

    @get:Input public val projectVersion: Property<String> = objects.property<String>()

    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    public val dokkaHtml: Provider<Directory> = layout.buildDirectory.dir("dokka/html/")

    @get:OutputDirectory
    public val apiDir: Provider<Directory> = layout.buildDirectory.dir("docs/_site/api/")

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
            buildSiteTask.configure { task ->
                task.projectVersion.set(project.provider { project.version.toString() })
                task.inputs.files(preBuildSiteTask)
            }

            val isDokkaEnabled = project.hubdleApi.isFullEnabled.get()
            val isMkdocsEnabled = project.hubdleSite.isFullEnabled.get()

            fun getDokkaTask(): TaskProvider<Task> = project.tasks.named("dokkaGenerate")
            fun getMkdocsBuildTask(): TaskProvider<Task> = project.tasks.named("mkdocsBuild")

            project.pluginManager.withPlugin(PluginId.JetbrainsDokka.id) {
                if (isDokkaEnabled) {
                    val dokkaGenerateTask: TaskProvider<Task> = getDokkaTask()
                    buildSiteTask.dependsOn(dokkaGenerateTask)
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
                    val dokkaGenerateTask: TaskProvider<Task> = getDokkaTask()
                    val mkdocsBuildTask: TaskProvider<Task> = getMkdocsBuildTask()
                    dokkaGenerateTask.configure { task -> task.dependsOn(mkdocsBuildTask) }
                }
            }

            return buildSiteTask
        }
    }

    private fun moveApiDocsInToDocs() {
        with(fileSystemOperations) {
            val dokkaHtmlPath: String = dokkaHtml.get().asFile.apply(File::mkdirs).path
            val apiDirPath: File = apiDir.get().asFile
            if (projectVersion.get().endsWith("-SNAPSHOT")) {
                copy { copy ->
                    copy.from(dokkaHtmlPath)
                    copy.into(apiDirPath.resolve("snapshot").path)
                }
            } else {
                apiDirPath.resolve("index.html").apply {
                    createNewFile()
                    writeText(apiIndexHtmlContent)
                }
                copy { copy ->
                    copy.from(dokkaHtmlPath)
                    copy.into(apiDirPath.resolve("versions/$projectVersion").path)
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
