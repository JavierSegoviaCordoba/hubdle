@file:OptIn(ExperimentalStdlibApi::class)

import java.text.SimpleDateFormat
import java.util.Date
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
        doLast {
            improveChangelog()
        }
    }

    register("mergeChangelog") {
        doLast {
            if ("${project.version}".all { it.isDigit() || it == '.' }) {
                mergeChangelog("${project.version}")
            }
        }
    }
}

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

fun mergeChangelog(version: String) {
    val changelog = changelogFile.readLines()

    val header = "## [$version] - ${SimpleDateFormat("yyyy-MM-dd").format(Date())}\n"

    fun String.containVersion(version: String): Boolean =
        contains("## [$version-") || contains("## [$version]")

    val firstVersionIndex = changelog.indexOfFirst { it.containVersion(version) }
    val lastVersionIndex = changelog.indexOfLast { it.containVersion(version) }

    val firstOldVersion =
        changelog.subList(lastVersionIndex + 1, changelog.count()).firstOrNull {
            it.contains("## [")
        }
    val firstOldVersionIndex =
        if (firstOldVersion != null) changelog.indexOf(firstOldVersion) else changelog.count()

    val versionBlock =
        if (firstOldVersionIndex == -1) changelog.subList(firstVersionIndex, firstOldVersionIndex)
        else changelog.subList(firstVersionIndex, firstOldVersionIndex)

    val mergedVersion = mutableListOf<String>()
    mergedVersion.add(header)
    mergedVersion.addAll(extractAllBlocks(versionBlock))

    val mergedChangelog = mutableListOf<String>()
    mergedChangelog.addAll(changelog.subList(0, firstVersionIndex - 1))
    mergedChangelog.add("")
    mergedChangelog.addAll(mergedVersion)
    mergedChangelog.addAll(changelog.subList(firstOldVersionIndex, changelog.count()))

    changelogFile.writeText(mergedChangelog.joinToString("\n"))
}

fun extractAllBlocks(versionBlock: List<String>): List<String> {

    val allBlocks = mutableListOf<String>()

    extractBlock("Added", versionBlock).run { if (isNotEmpty()) allBlocks.addAll(this) }
    extractBlock("Changed", versionBlock).run { if (isNotEmpty()) allBlocks.addAll(this) }
    extractBlock("Deprecated", versionBlock).run { if (isNotEmpty()) allBlocks.addAll(this) }
    extractBlock("Removed", versionBlock).run { if (isNotEmpty()) allBlocks.addAll(this) }
    extractBlock("Fixed", versionBlock).run { if (isNotEmpty()) allBlocks.addAll(this) }
    extractBlock("Updated", versionBlock).run { if (isNotEmpty()) allBlocks.addAll(this) }

    return allBlocks
}

fun extractBlock(blockName: String, versionBlock: List<String>): List<String> {
    val block = mutableListOf<String>()
    block.add("### $blockName")
    val indexes =
        versionBlock
            .mapIndexed { index, line -> if (line.contains("### $blockName")) index else null }
            .filterNotNull()

    for (i in indexes) {
        val changes = versionBlock.subList(i + 1, versionBlock.count())
        block.addAll(changes.takeWhile { it.replace(" ", "").startsWith("-") })
    }
    block.add("")

    return if (block.any { it.replace(" ", "").startsWith("-") }) block else emptyList()
}
