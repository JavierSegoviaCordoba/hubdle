package com.javiersc.hubdle.project.extensions.config.documentation.site

import com.javiersc.gradle.tasks.extensions.maybeRegisterLazily
import com.javiersc.gradle.tasks.extensions.namedLazily
import java.io.File
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.ProjectLayout
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskCollection
import org.gradle.api.tasks.TaskProvider
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import ru.vyarus.gradle.plugin.mkdocs.task.MkdocsBuildTask

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
            preBuildDocsTask: TaskProvider<PrebuildSiteTask>
        ): TaskCollection<BuildSiteTask> {
            val dokkaHtmlMultiModuleTask =
                project.tasks.namedLazily<DokkaMultiModuleTask>("dokkaHtmlMultiModule")
            val mkdocsBuildTask = project.tasks.namedLazily<MkdocsBuildTask>("mkdocsBuild")
            mkdocsBuildTask.configureEach { task -> task.dependsOn(preBuildDocsTask) }

            dokkaHtmlMultiModuleTask.configureEach { task -> task.dependsOn(mkdocsBuildTask) }

            val buildSiteTask = project.tasks.maybeRegisterLazily<BuildSiteTask>(name)
            buildSiteTask.configureEach { task ->
                task.notCompatibleWithConfigurationCache("mkDocsBuild(grgit) task is incompatible")
                task.dependsOn(mkdocsBuildTask)
                task.dependsOn(dokkaHtmlMultiModuleTask)
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
