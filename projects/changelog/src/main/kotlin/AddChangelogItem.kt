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
        description = "Extract dependencies from the table in the PR body created by Renovate"
    )
    @Optional
    var renovate: String? = null

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
        val dependencies: List<String> = dependenciesFromRenovatePullRequestBody(renovate)
        if (dependencies.isNotEmpty()) {
            logger.lifecycle("### Updated")
            for (dependency in dependencies) {
                logger.lifecycle("- $dependency")
            }
            changelogFile.writeText(changelog.addChanges("### Updated", dependencies))
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

private fun dependenciesFromRenovatePullRequestBody(body: String?): List<String> =
    body?.split("""\n""")
        .orEmpty()
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
