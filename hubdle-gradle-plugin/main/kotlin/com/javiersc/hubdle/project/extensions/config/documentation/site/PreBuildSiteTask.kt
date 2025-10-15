@file:Suppress("TooManyFunctions")

package com.javiersc.hubdle.project.extensions.config.documentation.site

import com.javiersc.kotlin.stdlib.endWithNewLine
import com.javiersc.kotlin.stdlib.removeDuplicateEmptyLines
import java.io.File
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.ProjectLayout
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated

@CacheableTask
public abstract class PreBuildSiteTask
@Inject
constructor(
    private val layout: ProjectLayout,
    private val fileSystemOperations: FileSystemOperations,
) : DefaultTask() {

    init {
        group = "documentation"
    }

    @get:Internal
    public val rootDirectory: File
        get() = layout.projectDirectory.asFile

    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    public val dotDocsDirectory: File
        get() = File("${layout.projectDirectory.asFile}/.docs/").apply(File::mkdirs)

    @get:Nested public abstract val projectsInfo: ListProperty<ProjectInfo>

    @get:Input public abstract val qodana: Property<Boolean>

    @get:Optional @get:Input public abstract val qodanaProjectKey: Property<String>

    @get:Input public abstract val sonar: Property<Boolean>

    @get:Optional @get:Input public abstract val sonarProjectId: Property<String>

    @get:OutputDirectory
    public val outputDotDocsDirectory: File
        get() = dotDocsDirectory

    @get:OutputDirectory
    public val buildDotDocsDirectory: File
        get() = File("${layout.projectDirectory.asFile}/build/.docs/").apply(File::mkdirs)

    @TaskAction
    public fun run() {
        buildDotDocsDirectory(dotDocsDirectory)
        fileSystemOperations.buildBuildDotDocs(
            rootDirectory,
            dotDocsDirectory,
            buildDotDocsDirectory,
        )
        buildChangelogInDocs(rootDirectory, fileSystemOperations)
        buildApiInDocs()
        buildProjectsInDocs(rootDirectory, projectsInfo.get(), fileSystemOperations)
        buildAnalysisInDocs()
        sanitizeMkDocsFile()
    }

    public companion object {
        public const val NAME: String = "preBuildSite"

        internal fun register(
            project: Project,
            configure: PreBuildSiteTask.() -> Unit,
        ): TaskProvider<PreBuildSiteTask> =
            project.tasks.register<PreBuildSiteTask>(NAME) { configure(this) }
    }

    private fun buildDotDocsDirectory(dotDocsDirectory: File) {
        val mkDocsFile = File("$dotDocsDirectory/mkdocs.yml")
        if (!mkDocsFile.exists()) {
            mkDocsFile.apply {
                ensureParentDirsCreated()
                createNewFile()
                writeText(defaultMkdocsYaml)
            }

            File("$dotDocsDirectory/docs/css/all.css").apply {
                ensureParentDirsCreated()
                createNewFile()
                writeText(defaultAllCss)
            }

            File("$dotDocsDirectory/docs/assets/empty.file").apply {
                ensureParentDirsCreated()
                createNewFile()
            }
        }
    }

    private val defaultMkdocsYaml: String
        get() =
            """
            |# TODO: Change all necessary properties
            |site_name: NAME # TODO
            |site_description: DESCRIPTION # TODO
            |site_author: AUTHOR # TODO
            |remote_branch: gh-pages
            |
            |repo_name: REPO NAME # TODO
            |repo_url: https://github.com/USER/REPO-NAME # TODO
            |
            |copyright: 'Copyright &copy; 2021 AUTHOR' # TODO
            |
            |theme:
            |  name: 'material'
            |  language: 'en'
            |  # TODO favicon: 'assets/favicon.png'
            |  # TODO logo: 'assets/logo.svg'
            |  font:
            |    text: 'Fira Sans'
            |    code: 'JetBrains Mono'
            |  palette:
            |    - media: "(prefers-color-scheme: light)"
            |      scheme: default
            |      primary: 'white'
            |      accent: 'white'
            |      toggle:
            |        icon: material/weather-sunny
            |        name: Switch to dark mode
            |    - media: "(prefers-color-scheme: dark)"
            |      primary: 'indigo'
            |      accent: 'light blue'
            |      scheme: slate
            |      toggle:
            |        icon: material/weather-night
            |        name: Switch to light mode
            |
            |nav:
            |  - Overview: index.md
            |
            |plugins:
            |  - search
            |
            |markdown_extensions:
            |  - admonition
            |  - smarty
            |  - codehilite:
            |      guess_lang: false
            |      linenums: True
            |  - footnotes
            |  - meta
            |  - toc:
            |      permalink: true
            |  - pymdownx.betterem:
            |      smart_enable: all
            |  - pymdownx.caret
            |  - pymdownx.details
            |  - pymdownx.inlinehilite
            |  - pymdownx.magiclink
            |  - pymdownx.smartsymbols
            |  - pymdownx.superfences
            |  - tables
            |
            |extra:
            |  social:
            |    - icon: fontawesome/brands/github
            |      link: https://github.com/USER # TODO
            |    - icon: fontawesome/brands/twitter
            |      link: https://twitter.com/USER # TODO
            |
            |extra_css:
            |  - css/all.css
            |"""
                .trimMargin()

    private val defaultAllCss: String
        get() =
            """
            |code {
            |    font-weight: 600;
            |}
            |
            |@media screen and (min-width: 76.1875em) {
            |    .md-nav--primary .md-nav__title {
            |        display: none;
            |    }
            |}
            |
            |.md-nav__link--active {
            |    font-weight: bold;
            |}
            |"""
                .trimMargin()

    private fun FileSystemOperations.buildBuildDotDocs(
        rootDirectory: File,
        dotDocsDirectory: File,
        buildDotDocsDirectory: File,
    ) {
        copy { copy ->
            copy.from(dotDocsDirectory)
            copy.into(buildDotDocsDirectory)
        }

        if (File("$dotDocsDirectory/docs/index.md").exists().not()) {
            copy { copy ->
                copy.from("$rootDirectory/README.md")
                copy.into("$rootDirectory/build/.docs/docs")
                copy.rename { fileName -> fileName.replace(fileName, "index.md") }
            }

            File("$rootDirectory/build/.docs/docs/index.md").apply {
                writeText(
                    readLines().joinToString("\n") { line ->
                        line.replace(".docs/docs/assets", "assets")
                    }
                )
            }
        }
    }

    private fun buildApiInDocs() {
        val docsNavigation = getDocsNavigation()
        val navsPlusApiDocs =
            docsNavigation.navs +
                """
                |  - API docs:
                |    - Latest: api/
                |    - Snapshot: api/snapshot/
                """
                    .trimMargin()

        writeNavigation(navsPlusApiDocs)
    }

    private fun buildChangelogInDocs(
        rootDirectory: File,
        fileSystemOperations: FileSystemOperations,
    ) {
        with(fileSystemOperations) {
            if (File("$rootDirectory/CHANGELOG.md").exists()) {
                copy { copy ->
                    copy.from("$rootDirectory/CHANGELOG.md")
                    copy.into("$rootDirectory/build/.docs/docs")
                }

                File("$rootDirectory/build/.docs/docs/CHANGELOG.md").apply {
                    val content = readLines()
                    val firstVersionLine =
                        content.indexOfFirst { line ->
                            line.contains("## [") && !line.contains("[Unreleased]")
                        } - 1

                    runCatching {
                        val contentUpdated =
                            (content.subList(0, 1) +
                                    content.subList(firstVersionLine, content.count()))
                                .joinToString("\n")
                        writeText(contentUpdated)
                    }
                }

                val docsNavigation = getDocsNavigation()
                val navsPlusChangelog = docsNavigation.navs + "  - Changelog: CHANGELOG.md"

                writeNavigation(navsPlusChangelog)
            }
        }
    }

    private fun buildProjectsInDocs(
        rootDirectory: File,
        projectsInfo: List<ProjectInfo>,
        fileSystemOperations: FileSystemOperations,
    ) {
        with(fileSystemOperations) {
            val projectsPath = "$rootDirectory/build/.docs/docs/projects"

            projectsInfo.forEach { projectInfo ->
                val projectsNavPath = "$projectsPath/${projectInfo.filePath}"

                copy { copy ->
                    copy.from(projectInfo.mdFile)
                    copy.into(projectsNavPath)
                    copy.rename { fileName -> fileName.replace(fileName, "${projectInfo.name}.md") }
                }

                File("$projectsNavPath/${projectInfo.name}.md").apply {
                    writeText(
                        readLines().joinToString("\n") { line ->
                            line.replace(".docs/docs/assets", "assets")
                        }
                    )
                }
            }

            val docsNavigation = getDocsNavigation()
            val navProjects: List<String> =
                buildMap<String, List<String>> {
                        projectsInfo.forEach { projectInfo ->
                            val fullPath =
                                "projects/${projectInfo.projectPath.drop(1).replace(":", "/")}"
                            val parentPath = fullPath.replaceAfterLast(projectInfo.name, "")
                            val projects = this[parentPath].orEmpty()
                            val paths = parentPath.split("/")
                            if (paths.isNotEmpty()) {
                                paths.reduce { previousPath, path ->
                                    val accumulatedPath = "$previousPath/$path"
                                    put(accumulatedPath, this[accumulatedPath].orEmpty())
                                    accumulatedPath
                                }
                            }
                            put(parentPath, projects + projectInfo.name)
                        }
                    }
                    .flatMap { (path, projects) ->
                        val count = path.count { it == '/' } + 1
                        val indent = buildString { repeat(count) { append("  ") } }
                        buildList {
                            add("- ${path.split('/').last()}:".prependIndent(indent))
                            addAll(
                                projects.map { project ->
                                    "- $project: $path/$project.md".prependIndent("$indent  ")
                                }
                            )
                        }
                    }
                    .cleanNavProjects()

            val navsPlusProjects = docsNavigation.navs + "  - Projects:" + navProjects

            writeNavigation(navsPlusProjects)
        }
    }

    private fun buildAnalysisInDocs() {
        val qodana = this.qodana.get()
        val qodanaProjectKey = this.qodanaProjectKey.orNull
        val sonar = this.sonar.get()
        val sonarProjectId = this.sonarProjectId.orNull
        val qodanaUrlProjects = "https://qodana.cloud/projects"
        val sonarUrlId = "https://sonarcloud.io/project/overview?id"
        val newTab = """" target="_blank"""
        val navReports: String =
            buildList {
                    if (qodana || sonar) add("  - Analysis:")
                    if (qodana) add("    - Qodana: $qodanaUrlProjects/$qodanaProjectKey$newTab")
                    if (sonar) add("    - Sonar: $sonarUrlId=$sonarProjectId$newTab")
                }
                .joinToString("\n")

        val docsNavigation = getDocsNavigation()

        val navsPlusReports = docsNavigation.navs + navReports.lines()

        writeNavigation(navsPlusReports)
    }

    private val mkDocsBuildFile: File
        get() =
            File("${layout.projectDirectory.asFile}/build/.docs/mkdocs.yml").apply {
                ensureParentDirsCreated()
                createNewFile()
            }

    private data class DocsNavigation(val index: Int, val navs: Set<String>)

    private fun writeNavigation(newNavigations: Set<String>) {
        mkDocsBuildFile.writeText(
            buildList {
                    addAll(mkDocsBuildFile.readLines())
                    removeAt(getDocsNavigation().index)
                    removeAll(getDocsNavigation().navs)
                    add("")
                    add("nav:")
                    addAll(newNavigations)
                    add("")
                }
                .joinToString("\n")
        )
    }

    private fun getDocsNavigation(): DocsNavigation {
        val content = mkDocsBuildFile.readLines()
        val navIndex = content.indexOfFirst { it.replace(" ", "").startsWith("nav:", true) }
        return DocsNavigation(
            index = navIndex,
            navs =
                content
                    .subList(navIndex + 1, content.count())
                    .takeWhile { it.replace(" ", "").startsWith("-") }
                    .toSet(),
        )
    }

    private fun List<String>.cleanNavProjects(): List<String> =
        buildList {
                val lines = this@cleanNavProjects

                fun String.isReference() =
                    filterNot(Char::isWhitespace).replaceBefore(":", "").drop(1).isEmpty()

                fun String.isReferenceOf(reference: String) =
                    trimIndent().takeWhile { it != ':' } ==
                        reference.trimIndent().takeWhile { it != ':' }

                if (lines.isNotEmpty()) {
                    lines.reduce { previous, line ->
                        when {
                            previous == line -> {
                                removeLast()
                                add(line)
                                line
                            }
                            previous.isReference() && line.isReferenceOf(previous) -> {
                                add(previous.takeWhile(Char::isWhitespace) + line.trimIndent())
                                line
                            }
                            line.isReferenceOf(previous) && line.isReference() -> {
                                add(previous)
                                previous
                            }
                            else -> {
                                add(previous)
                                line
                            }
                        }
                    }
                }
            }
            .distinctBy(String::trimIndent)

    private fun sanitizeMkDocsFile() {
        val content = mkDocsBuildFile.readLines().removeDuplicateEmptyLines().endWithNewLine()
        mkDocsBuildFile.writeText(content)
    }
}
