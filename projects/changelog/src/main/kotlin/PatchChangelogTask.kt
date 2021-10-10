import org.gradle.api.Project

val Project.changelogWithNoBlankLines: List<String>
    get() = changelogFile.readLines().filter { it.isNotBlank() }

val h1Regex = Regex("""^(#)(\s)(.*)${'$'}""")
val unreleasedRegex = Regex("""^(##)(\s)(\[Unreleased\])(.*)${'$'}""")
val h2Regex = Regex("""^(##)(\s)(.*)${'$'}""")
val h3Regex = Regex("""^(###)(\s)(.*)${'$'}""")

fun Project.patchChangelog() = changelogFile.writeText(buildChangelogAsString())

fun Project.buildChangelogAsString(): String =
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

@OptIn(ExperimentalStdlibApi::class)
fun Project.buildChangelog(): List<String> =
    buildList {
            val lastReleaseIndex =
                changelogWithNoBlankLines.indexOfFirst {
                    !unreleasedRegex.matches(it) && h2Regex.matches(it)
                }

            addPreRelease(this@buildChangelog, lastReleaseIndex)
            addRelease(this@buildChangelog, lastReleaseIndex)
        }
        .filter(String::isNotBlank)

fun MutableList<String>.addPreRelease(project: Project, lastReleaseIndex: Int) {
    project.changelogWithNoBlankLines.subList(0, lastReleaseIndex).forEach { line ->
        when {
            h1Regex.matches(line) -> add(line)
            unreleasedRegex.matches(line) -> add(line)
            h2Regex.matches(line) -> add(line)
            h3Regex.matches(line) -> add(line)
            else -> add(line)
        }
    }
}

fun MutableList<String>.addRelease(project: Project, lastReleaseIndex: Int) {
    val releasesChangelog =
        project.changelogWithNoBlankLines.subList(
            lastReleaseIndex,
            project.changelogWithNoBlankLines.size
        )

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
