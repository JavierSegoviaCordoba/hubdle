import com.javiersc.plugins.core.isSignificant
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper
import ru.vyarus.gradle.plugin.mkdocs.MkdocsExtension

plugins {
    id("org.jetbrains.dokka")
    id("ru.vyarus.mkdocs")
}

allprojects {
    afterEvaluate {
        if (plugins.asSequence().mapNotNull { (it as? KotlinBasePluginWrapper) }.count() > 0) {
            plugins.apply("org.jetbrains.dokka")
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
        copy {
            from("$rootDir/.docs")
            into("$rootDir/build/.docs")
        }

        if (file("$rootDir/.docs/index.md").exists().not()) {
            copy {
                from("$rootDir/README.md")
                into("$rootDir/build/.docs/docs")
                rename { fileName -> fileName.replace(fileName, "index.md") }
            }
        }

        buildChangelogInDocs()
        buildApiDocsInDocs()
        buildProjectsInDocs()

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

            val contentUpdated =
                (content.subList(0, 1) + content.subList(firstVersionLine, content.count()))
                    .joinToString("\n")
            writeText(contentUpdated)
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

@OptIn(ExperimentalStdlibApi::class)
fun buildProjectsInDocs() {
    class ProjectInfo(val name: String, val mdFile: File)

    val projectsInfo =
        subprojects.map { project ->
            val mdFiles =
                project.projectDir.walkTopDown().maxDepth(1).filter { file ->
                    file.name.endsWith(".md", true)
                }
            val mdFile =
                mdFiles.find { file -> file.name.contains("MODULE", true) }
                    ?: mdFiles.find { file -> file.name.contains("README", true) }
                    ?: mdFiles.first()

            ProjectInfo(project.name, mdFile)
        }

    projectsInfo.map {
        copy {
            from(it.mdFile)
            into("$rootDir/build/.docs/docs/projects")
            rename { fileName -> fileName.replace(fileName, "${it.name}.md") }
        }
    }

    val docsNavigation = getDocsNavigation()
    val navsPlusProjects =
        docsNavigation.navs +
            "  - Projects:" +
            projectsInfo.map { "    - ${it.name}: projects/${it.name}.md" }

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
