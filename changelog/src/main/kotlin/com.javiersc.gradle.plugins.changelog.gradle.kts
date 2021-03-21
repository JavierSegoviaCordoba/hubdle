@file:OptIn(ExperimentalStdlibApi::class)

import org.jetbrains.changelog.ChangelogPluginExtension
import org.jetbrains.changelog.closure
import org.jetbrains.changelog.date
import org.jetbrains.changelog.tasks.PatchChangelogTask

plugins { id("org.jetbrains.changelog") }

configure<ChangelogPluginExtension> {
    version = "${project.version}"
    header = closure { "[$version] - ${date()}" }
    groups = listOf("Added", "Changed", "Deprecated", "Removed", "Fixed", "Updated")
}

tasks {
    named<PatchChangelogTask>("patchChangelog") {
        doLast { improveChangelog(file("$projectDir/CHANGELOG.md")) }
    }
}

fun improveChangelog(changelogFile: File) {
    val changelogWithoutBlankLines = changelogFile.readLines().filter { it.isNotBlank() }

    val h1Regex = Regex("""^(#)(\s)(.*)${'$'}""")
    val unreleasedRegex = Regex("""^(##)(\s)(\[Unreleased\])(.*)${'$'}""")
    val h2Regex = Regex("""^(##)(\s)(.*)${'$'}""")
    val h3Regex = Regex("""^(###)(\s)(.*)${'$'}""")

    val lastReleaseIndex =
            changelogWithoutBlankLines.indexOfFirst {
                !unreleasedRegex.matches(it) && h2Regex.matches(it)
            }.let { index -> if (index == -1) changelogWithoutBlankLines.size else index }

    val preReleasesChangelog = changelogWithoutBlankLines.subList(0, lastReleaseIndex)
    val releasesChangelog =
        changelogWithoutBlankLines.subList(lastReleaseIndex, changelogWithoutBlankLines.size)

    val changelogToWrite =
        buildList {
                preReleasesChangelog.forEach { line ->
                    when {
                        h1Regex.matches(line) -> add(line)
                        unreleasedRegex.matches(line) -> add(line)
                        h2Regex.matches(line) -> add(line)
                        h3Regex.matches(line) -> add(line)
                        else -> add(line)
                    }
                }

                releasesChangelog.onEachIndexed { index, line ->
                    when {
                        unreleasedRegex.matches(line) -> add(line)
                        h1Regex.matches(line) -> add(line)
                        h2Regex.matches(line) -> add(line)
                        h3Regex.matches(line) &&
                            (h3Regex.matches(releasesChangelog[index + 1]) ||
                                h2Regex.matches(releasesChangelog[index + 1])) -> add("")
                        h3Regex.matches(line) -> add(line)
                        else -> add(line)
                    }
                }
            }
            .filter(String::isNotBlank)

    val changelogToWriteWithNoChanges =
        changelogToWrite
            .mapIndexed { index, line ->
                val nextLine = changelogToWrite.getOrElse(index + 1) { "" }
                if (!unreleasedRegex.matches(line) &&
                        h2Regex.matches(line) &&
                        h2Regex.matches(nextLine)
                ) {
                    "$line\n- No changes"
                } else {
                    line
                }
            }
            .joinToString("\n") { line ->
                when {
                    h1Regex.matches(line) -> line
                    unreleasedRegex.matches(line) -> "\n$line"
                    h2Regex.matches(line) -> "\n$line"
                    h3Regex.matches(line) -> "\n$line"
                    else -> line
                }
            }
            .replace("- No changes", "\n- No changes")
            .replaceFirst("### Updated", "### Updated\n") + "\n"

    changelogFile.writeText(changelogToWriteWithNoChanges)
}
