import org.jetbrains.changelog.ChangelogPluginExtension
import org.jetbrains.changelog.closure
import org.jetbrains.changelog.date
import org.jetbrains.changelog.tasks.PatchChangelogTask

plugins {
    id("org.jetbrains.changelog")
}

configure<ChangelogPluginExtension> {
    version = "${project.version}"
    header = closure { "[$version] - ${date()}" }
    groups = listOf("Added", "Changed", "Deprecated", "Removed", "Fixed", "Updated")
}

tasks {
    named<PatchChangelogTask>("patchChangelog") {
        doLast { improveChangelog(File("$projectDir/CHANGELOG.md"), "Unreleased") }
    }
}

fun improveChangelog(changelogFile: File, unreleasedFlag: String) {
    val filteredChangelog = changelogFile.readLines().filter { it.isNotBlank() }

    val startIndex =
        filteredChangelog.indexOfFirst { it.contains("## [") && !it.contains(unreleasedFlag) }

    val changelogToWrite = buildString {
        filteredChangelog.onEachIndexed { index, line ->
            when {
                !line.startsWith("###") && line.isNotBlank() -> appendLine(line + "\n")
                line.startsWith("###") && index < startIndex -> appendLine(line + "\n")
                line.startsWith("###") &&
                        filteredChangelog.getOrNull(index + 1)?.startsWith("###") == true -> Unit
                line.startsWith("###") &&
                        filteredChangelog.getOrNull(index + 1)?.startsWith("###") == false ->
                    appendLine(line + "\n")
            }
        }
    }.dropLastWhile { it.isWhitespace() } + " \n"

    changelogFile.writeText(changelogToWrite)
}
