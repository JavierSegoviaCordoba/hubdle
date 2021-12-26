package com.javiersc.gradle.plugins.docs

import com.javiersc.gradle.plugins.docs.internal.hasKotlinGradlePlugin
import java.io.File
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated
import ru.vyarus.gradle.plugin.mkdocs.MkdocsExtension

abstract class DocsPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply("ru.vyarus.mkdocs")
        target.pluginManager.apply("org.jetbrains.dokka")

        target.extensions.findByType(MkdocsExtension::class.java)?.apply {
            strict = false
            sourcesDir = "${target.rootProject.rootDir}/build/.docs"
            buildDir = "${target.rootProject.rootDir}/build/docs"
            publish.docPath = "_site"
        }

        target.allprojects { project ->
            project.afterEvaluate {
                if (it.hasKotlinGradlePlugin) {
                    it.pluginManager.apply(DokkaPlugin::class.java)
                    it.tasks.withType(DokkaTaskPartial::class.java) { dokkaTaskPartial ->
                        dokkaTaskPartial.dokkaSourceSets.configureEach { sourceSetBuilder ->
                            sourceSetBuilder.includes.from(listOf("MODULE.md"))
                        }
                    }
                }
            }
        }

        target.tasks.register("buildDocs") { task ->
            task.group = "documentation"

            task.project.buildDotDocsFolder()
            task.project.buildBuildDotDocs()
            task.project.buildChangelogInDocs()
            task.buildApiDocsInDocs()
            task.project.buildProjectsInDocs()
            task.project.buildReportsInDocs()
            task.project.sanitizeMkdocsFile()

            task.project.allprojects.onEach {
                runCatching { task.dependsOn(it.tasks.getByName("dokkaHtmlMultiModule")) }
            }
            task.dependsOn("mkdocsBuild")
        }
    }
}

private val Project.apiIndexHtmlContent: String
    get() =
        """
            <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <meta http-equiv="refresh" content="0;URL='versions/$version'" />
            </head>
            <body>
            </body>
            </html>
        """.trimIndent()

private fun Project.buildDotDocsFolder() {
    val dotDocsFile = file("$rootDir/.docs")
    if (dotDocsFile.exists().not()) {
        file("$dotDocsFile/mkdocs.yml").apply {
            ensureParentDirsCreated()
            createNewFile()
            writeText(
                """|# TODO: Change all necessary properties
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
                   |
                """.trimMargin()
            )
        }

        file("$dotDocsFile/docs/css/all.css").apply {
            ensureParentDirsCreated()
            createNewFile()
            writeText(
                """
                    code {
                        font-weight: 600;
                    }

                    @media screen and (min-width: 76.1875em) {
                        .md-nav--primary .md-nav__title {
                            display: none;
                        }
                    }

                    .md-nav__link--active {
                        font-weight: bold;
                    }

                """.trimIndent()
            )
        }

        file("$dotDocsFile/docs/assets/empty.file").apply {
            ensureParentDirsCreated()
            createNewFile()
        }
    }
}

private fun Project.buildBuildDotDocs() {
    copy {
        it.from("$rootDir/.docs")
        it.into("$rootDir/build/.docs")
    }

    if (file("$rootDir/.docs/docs/index.md").exists().not()) {
        copy {
            it.from("$rootDir/README.md")
            it.into("$rootDir/build/.docs/docs")
            it.rename { fileName -> fileName.replace(fileName, "index.md") }
        }

        file("$rootDir/build/.docs/docs/index.md").apply {
            writeText(
                readLines().joinToString("\n") { line ->
                    line.replace(".docs/docs/assets", "assets")
                }
            )
        }
    }
}

private fun Task.buildApiDocsInDocs() {
    val docsNavigation = project.getDocsNavigation()
    val navsPlusApiDocs =
        docsNavigation.navs +
            """
                |  - API docs:
                |    - Latest: api/
                |    - Snapshot: api/snapshot/
            """.trimMargin()

    project.writeNavigation(navsPlusApiDocs)

    doLast {
        val dokkaOutputDir = File("${project.rootProject.rootDir}/build/dokka/htmlMultiModule/")
        val apiDir = File("${project.rootProject.rootDir}/build/docs/_site/api/")
        if (project.version.toString().endsWith("-SNAPSHOT")) {
            project.copy {
                it.from(dokkaOutputDir.path)
                it.into(File("$apiDir/snapshot").path)
            }
        } else {
            project.file("$apiDir/index.html").apply {
                parentFile.mkdirs()
                if (!exists()) createNewFile()
                writeText(project.apiIndexHtmlContent)
            }
            project.copy {
                it.from(dokkaOutputDir.path)
                it.into(File("$apiDir/versions/${project.version}").path)
            }
        }
    }
}

private fun Project.buildChangelogInDocs() {
    if (file("$rootDir/CHANGELOG.md").exists()) {
        copy {
            it.from("$rootDir/CHANGELOG.md")
            it.into("$rootDir/build/.docs/docs")
        }

        file("$rootDir/build/.docs/docs/CHANGELOG.md").apply {
            val content = readLines()
            val firstVersionLine =
                content.indexOfFirst { line ->
                    line.contains("## [") && !line.contains("[Unreleased]")
                } - 1

            runCatching {
                val contentUpdated =
                    (content.subList(0, 1) + content.subList(firstVersionLine, content.count()))
                        .joinToString("\n")
                writeText(contentUpdated)
            }
        }

        val docsNavigation = getDocsNavigation()
        val navsPlusChangelog = docsNavigation.navs + "  - Changelog: CHANGELOG.md"

        writeNavigation(navsPlusChangelog)
    }
}

private data class ProjectInfo(val name: String, val projectPath: String, val mdFile: File) {

    val filePath: String = projectPath.split(':').filter(String::isNotEmpty).joinToString("/")
}

@OptIn(ExperimentalStdlibApi::class)
private fun Project.buildProjectsInDocs() {
    val projectsPath = "$rootDir/build/.docs/docs/projects"

    val projectsInfo: List<ProjectInfo> =
        subprojects.sortedBy(Project::getPath).mapNotNull { project ->
            val mdFiles =
                project.projectDir.walkTopDown().maxDepth(1).filter { file ->
                    file.name.endsWith(".md", true)
                }
            val mdFile =
                mdFiles.find { file -> file.name.contains("MODULE", true) }
                    ?: mdFiles.find { file -> file.name.contains("README", true) }
                        ?: mdFiles.firstOrNull()

            if (mdFile != null) {
                ProjectInfo(project.name, project.path, mdFile)
            } else {
                logger.lifecycle(
                    "${project.name} hasn't a markdown file, so it won't be added to docs"
                )
                null
            }
        }

    projectsInfo.forEach { projectInfo ->
        val projectsNavPath = "$projectsPath/${projectInfo.filePath}"

        copy {
            it.from(projectInfo.mdFile)
            it.into(projectsNavPath)
            it.rename { fileName -> fileName.replace(fileName, "${projectInfo.name}.md") }
        }

        file("$projectsNavPath/${projectInfo.name}.md").apply {
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
                    val fullPath = "projects/${projectInfo.projectPath.drop(1).replace(":", "/")}"
                    val parentPath = fullPath.replaceAfterLast(projectInfo.name, "")
                    val projects = this[parentPath].orEmpty()
                    val paths = parentPath.split("/")
                    paths.reduce { previousPath, path ->
                        val accumulatedPath = "$previousPath/$path"
                        put(accumulatedPath, this[accumulatedPath].orEmpty())
                        accumulatedPath
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

private fun Project.buildReportsInDocs() {
    fun createMdReportFile(title: String, pathAndFileName: String) {
        val content =
            """
                |# $title
                |
                |<iframe src="/reports-generated/$pathAndFileName" style="height: 65vh; width: 100vw; overflow: hidden" frameborder="0"></iframe>
                |
            """.trimMargin()

        file("$rootDir/build/.docs/docs/reports/$pathAndFileName.md").apply {
            ensureParentDirsCreated()
            createNewFile()
            writeText(content)
        }
    }

    createMdReportFile("All tests", "all-tests")
    createMdReportFile("Code analysis", "code-analysis")
    createMdReportFile("Code coverage", "code-coverage")
    createMdReportFile("Code quality", "code-quality")

    val docsNavigation = getDocsNavigation()

    val navReports: String =
        """
            |  - Reports:
            |    - All tests: reports/all-tests.md
            |    - Code analysis: reports/code-analysis.md
            |    - Code coverage: reports/code-coverage.md
            |    - Code quality: reports/code-quality.md
        """.trimMargin()

    val navsPlusReports = docsNavigation.navs + navReports.lines()

    writeNavigation(navsPlusReports)
}

private val Project.mkdocsBuildFile: File
    get() = file("$rootDir/build/.docs/mkdocs.yml")

private data class DocsNavigation(val index: Int, val navs: List<String>)

@OptIn(ExperimentalStdlibApi::class)
private fun Project.writeNavigation(newNavigations: List<String>) {
    mkdocsBuildFile.writeText(
        buildList {
                addAll(mkdocsBuildFile.readLines())
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

private fun Project.getDocsNavigation(): DocsNavigation {
    val content = mkdocsBuildFile.readLines()
    val navIndex = content.indexOfFirst { it.replace(" ", "").startsWith("nav:", true) }
    return DocsNavigation(
        index = navIndex,
        navs =
            content.subList(navIndex + 1, content.count()).takeWhile {
                it.replace(" ", "").startsWith("-")
            }
    )
}

@OptIn(ExperimentalStdlibApi::class)
private fun List<String>.cleanNavProjects(): List<String> =
    buildList {
            val lines = this@cleanNavProjects

            fun String.isReference() =
                filterNot(Char::isWhitespace).replaceBefore(":", "").drop(1).isEmpty()

            fun String.isReferenceOf(reference: String) =
                trimIndent().takeWhile { it != ':' } ==
                    reference.trimIndent().takeWhile { it != ':' }

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
        .distinctBy(String::trimIndent)

private fun Project.sanitizeMkdocsFile() {
    mkdocsBuildFile.writeText(
        mkdocsBuildFile.readLines().reduce { acc: String, b: String ->
            if (acc.lines().lastOrNull().isNullOrBlank() && b.isBlank()) acc else "$acc\n$b"
        } + "\n"
    )
}
