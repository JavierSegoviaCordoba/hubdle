@file:Suppress("SpreadOperator")

package com.javiersc.hubdle.extensions.config.documentation.changelog._internal

import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.Changelog.Reference
import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.Changelog.Version
import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.Changelog.Version.Group
import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.Changelog.Version.Group.Item
import com.javiersc.kotlin.stdlib.endWithNewLine
import com.javiersc.kotlin.stdlib.removeDuplicateEmptyLines
import com.javiersc.semver.Version as SemVer
import java.io.File

internal class Changelog(
    val header: String,
    val versions: List<Version>,
    val references: List<Reference>
) {

    override fun toString(): String =
        buildString {
                appendLine(header)
                appendLine()
                for (version in versions) {
                    append("$version")
                }
                if (versions.isNotEmpty()) appendLine()
                for (reference in references) {
                    appendLine(reference)
                    appendLine()
                }
            }
            .lines()
            .dropLastWhile(String::isBlank)
            .joinToString("\n")
            .removeDuplicateEmptyLines()
            .endWithNewLine()

    internal class Version(val value: String, val groups: List<Group>) {

        val version: String
            get() =
                if (value.contains("[")) value.substringAfter("[").substringBefore("]")
                else
                    value
                        .substringAfterLast('#')
                        .substringBefore(" - ")
                        .filterNot(Char::isWhitespace)

        override fun toString(): String = buildString {
            appendLine(value)
            appendLine()
            for (group in groups) {
                append("$group")
            }
            if (groups.isEmpty()) {
                appendLine("${Item.itemPrefix} ${Item.noChanges}")
                appendLine()
            }
        }

        internal class Group(val value: String, val items: List<Item>) {

            override fun toString(): String = buildString {
                appendLine(value)
                appendLine()
                for (item in items) {
                    appendLine("$item")
                }
                if (items.isNotEmpty()) appendLine()
            }

            internal class Item(val value: String) {

                override fun toString(): String = value.replace("- -", "-")
                companion object {
                    var itemPrefix: String = "-"
                    var noChanges: String = "No changes"
                    var unreleased: String = "Unreleased"
                }
            }
        }
    }

    internal class Reference(val value: String) {

        val version: String
            get() = value.substringAfter("[").substringBefore("]")

        override fun toString(): String = value
    }

    internal companion object
}

internal fun Changelog.Companion.fromFile(changelogFile: File): Changelog =
    Changelog.fromString(changelogFile.readText())

internal fun Changelog.Companion.fromString(changelogText: String): Changelog {
    val lines = changelogText.lines()
    val header = lines.first { it.startsWith("# ") }
    val versions: List<Version> = buildVersions(lines)
    val references: List<Reference> = buildReferences(lines)

    return Changelog(header, versions, references)
}

private fun buildVersions(lines: List<String>): List<Version> = buildList {
    val versions =
        buildMap<String, List<String>> {
            lines.filter(String::isNotBlank).filter(String::isNotReference).forEach { line ->
                when {
                    line.startsWith("## ") -> set(line, getOrDefault(line, emptyList()))
                    !line.startsWith("## ") -> {
                        val key = entries.lastOrNull()?.key
                        if (key != null) {
                            set(key, getOrDefault(key, emptyList()) + listOf(line))
                        }
                    }
                }
            }
        }

    versions.forEach { (value, items) -> add(Version(value, buildGroups(items))) }
}

private fun buildGroups(lines: List<String>): List<Group> = buildList {
    val groups =
        buildMap<String, List<String>> {
            lines.filter(String::isNotBlank).forEach { line ->
                when {
                    line.startsWith("### ") -> set(line, getOrDefault(line, emptyList()))
                    !line.startsWith("### ") -> {
                        val key = entries.lastOrNull()?.key
                        if (key != null) {
                            set(key, getOrDefault(key, emptyList()) + listOf(line))
                        }
                    }
                }
            }
        }
    groups.forEach { (value, items) -> add(Group(value, buildItems(items))) }
}

private fun buildReferences(lines: List<String>): List<Reference> {
    val index = lines.indexOfLast { it.startsWith("[Unreleased]:") }
    val references =
        if (index == -1) emptyList()
        else {
            listOf(Reference(lines[index])) +
                lines
                    .subList(index + 1, lines.lastIndex)
                    .filter(String::isNotBlank)
                    .map(::Reference)
                    .sortedByDescending { SemVer(it.version) }
        }
    return references
}

private fun buildItems(lines: List<String>): List<Item> =
    lines.filter(String::isNotBlank).filterNot { it.startsWith("#") }.map(::Item)

private val String.isNotReference: Boolean
    get() = !Regex("""^(\[.*]:)""").containsMatchIn(this)
