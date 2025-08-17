package com.javiersc.hubdle.project.extensions.config.documentation.changelog

import com.javiersc.hubdle.project.extensions.config.documentation.changelog._internal.Changelog
import com.javiersc.hubdle.project.extensions.config.documentation.changelog._internal.changelogFile
import com.javiersc.hubdle.project.extensions.config.documentation.changelog._internal.fromString
import com.javiersc.kotlin.stdlib.isNotNullNorEmpty
import com.javiersc.kotlin.stdlib.or
import java.io.File
import javax.inject.Inject
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Constants
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.file.ProjectLayout
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.gradle.kotlin.dsl.property

@CacheableTask
public open class AddChangelogItemTask
@Inject
constructor(rootDir: File, objects: ObjectFactory, layout: ProjectLayout) : DefaultTask() {

    @Input
    @Option(option = "added", description = "Add an item to the `added` section")
    @Optional
    public val added: Property<String> = objects.property()

    @Input
    @Option(option = "changed", description = "Add an item to the `changed` section")
    @Optional
    public val changed: Property<String> = objects.property()

    @Input
    @Option(option = "deprecated", description = "Add an item to the `deprecated` section")
    @Optional
    public val deprecated: Property<String> = objects.property()

    @Input
    @Option(option = "removed", description = "Add an item to the `removed` section")
    @Optional
    public val removed: Property<String> = objects.property()

    @Input
    @Option(option = "fixed", description = "Add an item to the `fixed` section")
    @Optional
    public val fixed: Property<String> = objects.property()

    @Input
    @Option(option = "updated", description = "Add an item to the `updated` section")
    @Optional
    public val updated: Property<String> = objects.property()

    @Input
    @Option(option = "renovate", description = "Extract dependencies from the table in the PR body")
    @Optional
    public val renovate: Property<String> = objects.property()

    @Input
    @Option(
        option = "renovatePath",
        description = "Extract dependencies from the table in the PR body from a file",
    )
    @Optional
    public val renovatePath: Property<String> = objects.property()

    @Input
    @Option(
        option = "renovateCommitTable",
        description =
            """
               Extract dependencies from the table in the commit body
               Add `"commitBodyTable": true` to Renovate config
            """,
    )
    public val renovateCommitTable: Property<Boolean> =
        objects.property<Boolean>().convention(false)

    @get:Internal
    internal val rootDir: RegularFileProperty = objects.fileProperty().convention { rootDir }

    @InputFile
    @PathSensitive(PathSensitivity.RELATIVE)
    public val changelogFileInput: RegularFileProperty =
        objects.fileProperty().convention(layout.projectDirectory.file("CHANGELOG.md"))

    @OutputFile
    public val changelogFileOutput: RegularFileProperty =
        objects.fileProperty().convention(layout.projectDirectory.file("CHANGELOG.md"))

    init {
        group = "changelog"
    }

    @TaskAction
    public fun run() {
        check(project.changelogFile.exists()) { "CHANGELOG.md file doesn't found" }
        setupSection("### Added", added.orNull)
        setupSection("### Changed", changed.orNull)
        setupSection("### Deprecated", deprecated.orNull)
        setupSection("### Removed", removed.orNull)
        setupSection("### Fixed", fixed.orNull)
        setupSection("### Updated", updated.orNull)
        setupRenovate()
    }

    public companion object {
        public const val NAME: String = "addChangelogItem"
    }
}

private fun AddChangelogItemTask.setupSection(header: String, item: String?) {
    if (item.isNotNullNorEmpty()) {
        val updatedChangelog: Changelog =
            changelogFileInput.get().asFile.readText().addChanges(header, listOf(item))
        changelogFileOutput.get().asFile.writeText(updatedChangelog.toString())
    }
}

private fun AddChangelogItemTask.setupRenovate() {
    val rootDir: File = rootDir.get().asFile

    val dependenciesFromPullRequest: List<String> =
        dependenciesFromRenovatePullRequestBody(rootDir, renovate.orNull, renovatePath.orNull)

    val dependenciesFromCommit: List<String> =
        if (renovateCommitTable.get()) dependenciesFromRenovateCommit(rootDir) else emptyList()

    val updatedLabel = "### Updated"

    when {
        dependenciesFromPullRequest.isNotEmpty() -> {
            logger.lifecycle(updatedLabel)
            for (dependencyFromPullRequest in dependenciesFromPullRequest) {
                logger.lifecycle("- $dependencyFromPullRequest")
            }

            val updatedChangelog: Changelog =
                changelogFileInput
                    .get()
                    .asFile
                    .readText()
                    .addChanges(updatedLabel, dependenciesFromPullRequest)
            changelogFileOutput.get().asFile.writeText(updatedChangelog.toString())
        }
        dependenciesFromCommit.isNotEmpty() -> {
            logger.lifecycle(updatedLabel)
            for (dependencyFromCommit in dependenciesFromCommit) {
                logger.lifecycle("- $dependencyFromCommit")
            }

            val updatedChangelog: Changelog =
                changelogFileInput
                    .get()
                    .asFile
                    .readText()
                    .addChanges(updatedLabel, dependenciesFromCommit)
            changelogFileOutput.get().asFile.writeText(updatedChangelog.toString())
        }
    }
}

private fun String.addChanges(header: String, changes: List<String>): Changelog =
    buildList<String> {
            val lastUnreleasedLineIndex: Int =
                lines()
                    .indexOfFirst { line -> line.isHeader && !line.isUnreleased }
                    .takeIf { it != -1 }
                    .or { lines().lastIndex }
            var shouldAddUpdate = true
            lines().onEach { line ->
                if (line.startsWith(header) && shouldAddUpdate) {
                    shouldAddUpdate = false
                    add(line)
                    for (change in changes) {
                        val unreleasedLines = lines().subList(0, lastUnreleasedLineIndex)
                        if (unreleasedLines.none { it.contains(change) }) {
                            add("- $change")
                        }
                    }
                } else {
                    add(line)
                }
            }
            runCatching {
                forEachIndexed { index: Int, line ->
                    val updateRegex = """(- `)(.*)( )(->)( )(.*)(`)"""
                    if (Regex(updateRegex).matches(line)) {
                        for (j in index + 1 until lastUnreleasedLineIndex) {
                            val lineToRemove = this[j]
                            val shouldRemovePreviousUpdate =
                                lineToRemove.module == line.module &&
                                    Regex(updateRegex).matches(lineToRemove)

                            if (shouldRemovePreviousUpdate) removeAt(j)
                        }
                    }
                }
            }
        }
        .joinToString("\n")
        .run(Changelog.Companion::fromString)

private fun dependenciesFromRenovatePullRequestBody(
    rootDir: File,
    body: String?,
    path: String?,
): List<String> {
    val renovateLines: List<String> =
        when {
            body?.isNotBlank() == true -> body.split("""\n""")
            path?.isNotBlank() == true -> File("$rootDir/$path").readText().split("\n")
            else -> emptyList()
        }

    return renovateLines
        .asSequence()
        .filter(String::isNotBlank)
        .map { it.replace("""\n""", "\n") }
        .dropWhile { it.startsWith("| Package | Change |").not() }
        .dropWhile { it.startsWith("|---").not() }
        .drop(1)
        .takeWhile { it.startsWith("| ") }
        .flatMap { it.split("|") }
        .map { it.replace(" ", "") }
        .map { if (it.startsWith("`").not()) it else it.replace("`", "").split("->")[1] }
        .filter(String::isNotBlank)
        .filter { it.startsWith("[!").not() }
        .map { if (it.startsWith("[")) it.drop(1).takeWhile { char -> char != ']' } else it }
        .zipWithNext { a: String, b: String ->
            if (a.first().run { isLetter() || this == '[' }) "`$a -> $b`" else null
        }
        .filterNotNull()
        .toList()
}

private fun dependenciesFromRenovateCommit(rootDir: File): List<String> {
    val gitFolder = rootDir.walkTopDown().first { it.name == ".git" }

    val repository: Repository =
        FileRepositoryBuilder().setGitDir(gitFolder).readEnvironment().findGitDir().build()

    val head = repository.resolve(Constants.HEAD).name

    val commits: List<RevCommit> =
        Git(repository).log().add(repository.resolve(head)).call().toList()

    val latestCommit: RevCommit =
        commits.first { commit ->
            listOf("datasource", "package", "from", "to").all { keyword ->
                keyword in commit.fullMessage
            }
        }

    return latestCommit.fullMessage
        .lines()
        .dropWhile { it.startsWith("| ----").not() }
        .drop(1)
        .dropLastWhile { it.startsWith("|").not() && it.endsWith("|").not() }
        .map {
            val data = it.filterNot(Char::isWhitespace).split("|").drop(2).dropLast(1)
            "`${data.first()} -> ${data.last()}`"
        }
        .distinct()
}

private val String.module: String
    get() =
        filterNot(Char::isWhitespace)
            .replace("`", "")
            .replaceAfter("->", "")
            .replace("->", "")
            .drop(1)

private val String.isHeader: Boolean
    get() = startsWith("## [") || startsWith("## ")
private val String.isUnreleased: Boolean
    get() = contains("## [Unreleased]", true) || contains("## Unreleased", true)
