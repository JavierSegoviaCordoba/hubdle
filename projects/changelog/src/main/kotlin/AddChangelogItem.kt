import java.io.File
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

abstract class AddChangelogItem : DefaultTask() {

    @get:Input
    @set:Option(option = "added", description = "Add an item to the `added` section")
    @Optional
    var added: String? = null

    @get:Input
    @set:Option(option = "changed", description = "Add an item to the `changed` section")
    @Optional
    var changed: String? = null

    @get:Input
    @set:Option(option = "deprecated", description = "Add an item to the `deprecated` section")
    @Optional
    var deprecated: String? = null

    @get:Input
    @set:Option(option = "removed", description = "Add an item to the `removed` section")
    @Optional
    var removed: String? = null

    @get:Input
    @set:Option(option = "fixed", description = "Add an item to the `fixed` section")
    @Optional
    var fixed: String? = null

    @get:Input
    @set:Option(option = "updated", description = "Add an item to the `updated` section")
    @Optional
    var updated: String? = null

    @get:Input
    @set:Option(
        option = "renovate",
        description = "Extract dependencies from the table in the PR body",
    )
    @Optional
    var renovate: String? = null

    @get:Input
    @set:Option(
        option = "renovatePath",
        description = "Extract dependencies from the table in the PR body from a file",
    )
    @Optional
    var renovatePath: String? = null

    @get:Input
    @set:Option(
        option = "renovateCommitTable",
        description =
            """
               Extract dependencies from the table in the commit body
               Add `"commitBodyTable": true` to the Renovate config
            """,
    )
    var renovateCommitTable: Boolean = false

    init {
        group = "changelog"
    }

    @TaskAction
    fun run() {
        check(project.changelogFile.exists()) { "CHANGELOG.md file doesn't found" }
        setupSection("### Added", added)
        setupSection("### Changed", changed)
        setupSection("### Deprecated", deprecated)
        setupSection("### Removed", removed)
        setupSection("### Fixed", fixed)
        setupSection("### Updated", updated)
        setupRenovate()
    }
}

private val Project.changelog: String
    get() = changelogFile.readText()

private fun AddChangelogItem.setupSection(header: String, item: String?) =
    with(project) {
        item?.let { item ->
            logger.lifecycle(header)
            logger.lifecycle("- $item")
            changelogFile.writeText(changelog.addChanges(header, listOf(item)))
        }
    }

private fun AddChangelogItem.setupRenovate(): Unit =
    with(project) {
        val dependenciesFromPullRequest: List<String> =
            dependenciesFromRenovatePullRequestBody(renovate, renovatePath)

        val dependenciesFromCommit: List<String> =
            if (renovateCommitTable) dependenciesFromRenovateCommit() else emptyList()

        val updatedLabel = "### Updated"

        when {
            dependenciesFromPullRequest.isNotEmpty() -> {
                logger.lifecycle(updatedLabel)
                for (dependencyFromPullRequest in dependenciesFromPullRequest) {
                    logger.lifecycle("- $dependencyFromPullRequest")
                }
                changelogFile.writeText(
                    changelog.addChanges(updatedLabel, dependenciesFromPullRequest),
                )
            }
            dependenciesFromCommit.isNotEmpty() -> {
                logger.lifecycle(updatedLabel)
                for (dependencyFromCommit in dependenciesFromCommit) {
                    logger.lifecycle("- $dependencyFromCommit")
                }
                changelogFile.writeText(changelog.addChanges(updatedLabel, dependenciesFromCommit))
            }
        }
    }

@OptIn(ExperimentalStdlibApi::class)
private fun String.addChanges(header: String, changes: List<String>): String =
    buildList {
            var shouldAddUpdate = true
            lines().onEach { line ->
                if (line.startsWith(header) && shouldAddUpdate) {
                    shouldAddUpdate = false
                    add(line)
                    for (change in changes) {
                        add("- $change")
                    }
                } else {
                    add(line)
                }
            }
        }
        .joinToString("\n")

private fun Project.dependenciesFromRenovatePullRequestBody(
    body: String?,
    path: String?
): List<String> {
    val renovateLines: List<String> =
        when {
            body != null && body.isNotBlank() -> body.split("""\n""")
            path != null -> File("$rootDir/$path").readText().split("\n")
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

private fun Project.dependenciesFromRenovateCommit(): List<String> {
    val gitFolder = File("${rootProject.rootDir}").walkTopDown().first { it.name == ".git" }

    val repository: Repository =
        FileRepositoryBuilder().setGitDir(gitFolder).readEnvironment().findGitDir().build()

    val head: Ref = repository.findRef(repository.fullBranch)
    val latestCommit: RevCommit = RevWalk(repository).parseCommit(head.objectId)

    return latestCommit
        .fullMessage
        .lines()
        .dropWhile { it.startsWith("| ----").not() }
        .drop(1)
        .dropLastWhile { it.startsWith("|").not() && it.endsWith("|").not() }
        .map {
            val data = it.filterNot(Char::isWhitespace).split("|").drop(2).dropLast(1)
            "`${data.first()} -> ${data.last()}`"
        }
}
