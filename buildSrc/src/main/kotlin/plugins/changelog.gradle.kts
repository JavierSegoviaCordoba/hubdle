@file:OptIn(ExperimentalStdlibApi::class)

import org.jetbrains.changelog.closure
import org.jetbrains.changelog.date

plugins { id("org.jetbrains.changelog") }

changelog {
    version = "${project.version}"
    header = closure { "[$version] - ${date()}" }
    groups = listOf("Added", "Changed", "Deprecated", "Removed", "Fixed", "Updated")
}

tasks { patchChangelog { doLast { improveChangelog() } } }

val changelogFile = file("$projectDir/CHANGELOG.md")
val changelogWithNoBlankLines
    get() = changelogFile.readLines().filter { it.isNotBlank() }

val h1Regex = Regex("""^(#)(\s)(.*)${'$'}""")
val unreleasedRegex = Regex("""^(##)(\s)(\[Unreleased\])(.*)${'$'}""")
val h2Regex = Regex("""^(##)(\s)(.*)${'$'}""")
val h3Regex = Regex("""^(###)(\s)(.*)${'$'}""")

fun improveChangelog() = changelogFile.writeText(buildChangelogAsString())

fun buildChangelog(): List<String> =
    buildList {
            val lastReleaseIndex =
                changelogWithNoBlankLines.indexOfFirst {
                    !unreleasedRegex.matches(it) && h2Regex.matches(it)
                }

            addPreRelease(lastReleaseIndex)
            addRelease(lastReleaseIndex)
        }
        .filter(String::isNotBlank)

fun MutableList<String>.addPreRelease(lastReleaseIndex: Int) {
    changelogWithNoBlankLines.subList(0, lastReleaseIndex).forEach { line ->
        when {
            h1Regex.matches(line) -> add(line)
            unreleasedRegex.matches(line) -> add(line)
            h2Regex.matches(line) -> add(line)
            h3Regex.matches(line) -> add(line)
            else -> add(line)
        }
    }
}

fun MutableList<String>.addRelease(lastReleaseIndex: Int) {
    val releasesChangelog =
        changelogWithNoBlankLines.subList(lastReleaseIndex, changelogWithNoBlankLines.size)

    runCatching {
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
        .onFailure { add("") }
}

fun buildChangelogAsString(): String =
    buildChangelog()
        .mapIndexed { index, line ->
            val nextLine = buildChangelog().getOrElse(index + 1) { "" }
            if (!unreleasedRegex.matches(line) && h2Regex.matches(line) && h2Regex.matches(nextLine)
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
        .replaceFirst("### Updated", "### Updated\n") + "\n"
