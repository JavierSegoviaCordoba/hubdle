@file:OptIn(ExperimentalStdlibApi::class)
@file:Suppress("SpreadOperator")

package com.javiersc.hubdle.extensions.config.documentation.changelog._internal

import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.Changelog.Version
import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.Changelog.Version.Group
import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.Changelog.Version.Group.Item
import java.io.File

internal class Changelog(val header: String, vararg val versions: Version) {

    override fun toString(): String =
        buildString {
                appendLine(header)
                appendLine()
                for (version in versions) {
                    append("$version")
                }
                if (versions.isNotEmpty()) appendLine()
            }
            .lines()
            .dropLastWhile(String::isBlank)
            .joinToString("\n") + "\n"

    internal class Version(val value: String, vararg val groups: Group) {

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

        internal class Group(val value: String, vararg val items: Item) {

            override fun toString(): String = buildString {
                appendLine(value)
                appendLine()
                for (item in items) {
                    appendLine("$item")
                }
                if (items.isNotEmpty()) appendLine()
            }

            internal class Item(val value: String) {

                override fun toString(): String = value

                companion object {
                    var itemPrefix: String = "-"
                    var noChanges: String = "No changes"
                    var unreleased: String = "Unreleased"
                }
            }
        }
    }
    internal companion object
}

internal fun Changelog.Companion.fromFile(changelogFile: File): Changelog =
    Changelog.fromString(changelogFile.readText())

internal fun Changelog.Companion.fromString(changelogText: String): Changelog {
    val lines = changelogText.lines()
    val header = lines.first { it.startsWith("# ") }
    val versions: Array<Version> = buildVersions(lines)

    return Changelog(header, *versions)
}

private fun buildVersions(lines: List<String>): Array<Version> =
    buildList {
            val versions =
                buildMap<String, List<String>> {
                    lines.filter(String::isNotBlank).forEach { line ->
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
            versions.forEach { (value, items) -> add(Version(value, *buildGroups(items))) }
        }
        .toTypedArray()

private fun buildGroups(lines: List<String>): Array<Group> =
    buildList {
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
            groups.forEach { (value, items) -> add(Group(value, *buildItems(items))) }
        }
        .toTypedArray()

private fun buildItems(lines: List<String>): Array<Item> =
    buildList {
            lines
                .filter(String::isNotBlank)
                .filterNot { it.startsWith("#") }
                .forEach { item -> add(Item(item)) }
        }
        .toTypedArray()
