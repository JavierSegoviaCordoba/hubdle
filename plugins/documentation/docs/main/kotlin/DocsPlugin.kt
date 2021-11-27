package com.javiersc.gradle.plugins.docs

import com.javiersc.gradle.plugins.docs.internal.hasKotlinGradlePlugin
import java.io.File
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
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
                    it.tasks.withType(DokkaMultiModuleTask::class.java) { dokkaMultiModuleTask ->
                        val dokkaDir = dokkaMultiModuleTask.project.buildDir.resolve("dokka")
                        dokkaMultiModuleTask.outputDirectory.set(dokkaDir)
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

            task.project.allprojects.onEach {
                runCatching { task.dependsOn(it.tasks.getByName("dokkaHtmlMultiModule")) }
            }
            task.dependsOn("mkdocsBuild")
        }
    }
}

val Project.apiIndexHtmlContent: String
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

fun Project.buildDotDocsFolder() {
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
                   |  palette:
                   |    primary: 'white'
                   |    accent: 'white'
                   |  font:
                   |    text: 'Fira Sans'
                   |    code: 'JetBrains Mono'
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

fun Project.buildBuildDotDocs() {
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

@OptIn(ExperimentalStdlibApi::class)
fun Task.buildApiDocsInDocs() {
    val docsNavigation = project.getDocsNavigation()
    val navsPlusApiDocs =
        docsNavigation.navs +
            """
           |  - API docs: 
           |        - Latest: api/
           |        - Snapshot: api/snapshot/
        """.trimMargin()

    project.mkdocsBuildFile.writeText(
        buildList<String> {
                addAll(project.mkdocsBuildFile.readLines())
                removeAt(docsNavigation.index)
                removeAll(docsNavigation.navs)
                add("")
                add("nav:")
                addAll(navsPlusApiDocs)
            }
            .joinToString("\n")
    )

    doLast {
        if (project.version.toString().endsWith("-SNAPSHOT")) {
            project.copy {
                it.from("${project.rootProject.rootDir}/build/dokka")
                it.into("${project.rootProject.rootDir}/build/docs/_site/api/snapshot")
            }
        } else {
            project.file("${project.rootProject.rootDir}/build/docs/_site/api/index.html").apply {
                parentFile.mkdirs()
                if (!exists()) createNewFile()
                writeText(project.apiIndexHtmlContent)
            }
            project.copy {
                it.from("${project.rootProject.rootDir}/build/dokka")
                it.into(
                    "${project.rootProject.rootDir}/build/docs/_site/api/versions/${project.version}"
                )
            }
        }
    }
}

@OptIn(ExperimentalStdlibApi::class)
fun Project.buildChangelogInDocs() {
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

        mkdocsBuildFile.writeText(
            buildList<String> {
                    addAll(mkdocsBuildFile.readLines())
                    removeAt(docsNavigation.index)
                    removeAll(docsNavigation.navs)
                    add("")
                    add("nav:")
                    addAll(navsPlusChangelog)
                }
                .joinToString("\n")
        )
    }
}

private data class ProjectInfo(val name: String, val projectPath: String, val mdFile: File) {

    val filePath: String = projectPath.split(':').filter(String::isNotEmpty).joinToString("/")
}

@OptIn(ExperimentalStdlibApi::class)
fun Project.buildProjectsInDocs() {
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

    mkdocsBuildFile.writeText(
        buildList<String> {
                addAll(mkdocsBuildFile.readLines())
                removeAt(docsNavigation.index)
                removeAll(docsNavigation.navs)
                add("")
                add("nav:")
                addAll(navsPlusProjects)
            }
            .joinToString("\n")
    )
}

val Project.mkdocsBuildFile: File
    get() = file("$rootDir/build/.docs/mkdocs.yml")

data class DocsNavigation(val index: Int, val navs: List<String>)

fun Project.getDocsNavigation(): DocsNavigation {
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
fun List<String>.cleanNavProjects(): List<String> =
    buildList<String> {
            val lines = this@cleanNavProjects

            fun String.isReference() =
                filterNot(Char::isWhitespace).replaceBefore(":", "").drop(1).isEmpty()

            fun String.isReferenceOf(reference: String) =
                trimIndent().takeWhile { it != ':' } ==
                    reference.trimIndent().takeWhile { it != ':' }

            lines.reduce { previous, line ->
                //        println("__________________________________________________")
                //        println(this.joinToString("\n"))
                //        println("**************************************************")
                //        println("PREVIOUS: ${previous.trimIndent()}")
                //        println("LINE:     ${line.trimIndent()}")
                //        println(previous.isReference())
                //        println(line.isReferenceOf(previous))

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
