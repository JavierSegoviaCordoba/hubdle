import com.javiersc.gradle.plugins.docs.internal.hasKotlinGradlePlugin
import com.javiersc.plugins.core.isSignificant
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated
import ru.vyarus.gradle.plugin.mkdocs.MkdocsExtension

plugins {
    id("org.jetbrains.dokka")
    id("ru.vyarus.mkdocs")
}

allprojects {
    afterEvaluate {
        if (hasKotlinGradlePlugin) {
            plugins.apply(DokkaPlugin::class)
            tasks {
                withType<DokkaTaskPartial>() {
                    dokkaSourceSets.configureEach { includes.from(listOf("MODULE.md")) }
                }
                withType<DokkaMultiModuleTask>().configureEach {
                    val dokkaDir = buildDir.resolve("dokka")
                    outputDirectory.set(dokkaDir)
                }
            }
        }
    }
}

tasks {
    withType<DokkaTaskPartial>() {
        dokkaSourceSets.configureEach { includes.from(listOf("MODULE.md")) }
    }

    withType<DokkaMultiModuleTask>().configureEach {
        val dokkaDir = buildDir.resolve("dokka")
        outputDirectory.set(dokkaDir)
    }

    register("buildDocs") {
        group = "documentation"

        buildDotDocsFolder()
        buildBuildDotDocs()
        buildChangelogInDocs()
        buildApiDocsInDocs()
        buildProjectsInDocs()

        allprojects.onEach { runCatching { dependsOn(it.tasks.getByName("dokkaHtmlMultiModule")) } }
        dependsOn("mkdocsBuild")
    }
}

configure<MkdocsExtension> {
    strict = false

    sourcesDir = "$rootDir/build/.docs"

    buildDir = "$rootDir/build/docs"

    publish.docPath = "_site"
}

val apiIndexHtmlContent: String
    get() =
        """
            <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <meta http-equiv="refresh" content="0;URL='versions/${project.version}'" />
            </head>
            <body>
            </body>
            </html>
        """.trimIndent()

fun buildDotDocsFolder() {
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

fun buildBuildDotDocs() {
    copy {
        from("$rootDir/.docs")
        into("$rootDir/build/.docs")
    }

    if (file("$rootDir/.docs/docs/index.md").exists().not()) {
        copy {
            from("$rootDir/README.md")
            into("$rootDir/build/.docs/docs")
            rename { fileName -> fileName.replace(fileName, "index.md") }
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
    val docsNavigation = getDocsNavigation()
    val navsPlusApiDocs =
        docsNavigation.navs +
            """
           |  - API docs: 
           |        - Latest: api/
           |        - Snapshot: api/snapshot/
        """.trimMargin()

    mkdocsBuildFile.writeText(
        buildList<String> {
                addAll(mkdocsBuildFile.readLines())
                removeAt(docsNavigation.index)
                removeAll(docsNavigation.navs)
                add("")
                add("nav:")
                addAll(navsPlusApiDocs)
            }
            .joinToString("\n")
    )

    doLast {
        if (isSignificant) {
            if (project.version.toString().endsWith("-SNAPSHOT")) {
                copy {
                    from("$rootDir/build/dokka")
                    into("$rootDir/build/docs/_site/api/snapshot")
                }
            } else {
                file("$rootDir/build/docs/_site/api/index.html").apply {
                    ensureParentDirsCreated()
                    if (!exists()) createNewFile()
                    writeText(apiIndexHtmlContent)
                }

                copy {
                    from("$rootDir/build/dokka")
                    into("$rootDir/build/docs/_site/api/versions/${project.version}")
                }
            }
        }
    }
}

@OptIn(ExperimentalStdlibApi::class)
fun buildChangelogInDocs() {
    if (file("$rootDir/CHANGELOG.md").exists()) {
        copy {
            from("$rootDir/CHANGELOG.md")
            into("$rootDir/build/.docs/docs")
        }

        File("$rootDir/build/.docs/docs/CHANGELOG.md").apply {
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
fun buildProjectsInDocs() {
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
            from(projectInfo.mdFile)
            into(projectsNavPath)
            rename { fileName -> fileName.replace(fileName, "${projectInfo.name}.md") }
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

val mkdocsBuildFile: File
    get() = file("$rootDir/build/.docs/mkdocs.yml")

data class DocsNavigation(val index: Int, val navs: List<String>)

fun getDocsNavigation(): DocsNavigation {
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
