@file:Suppress("SpreadOperator")

package com.javiersc.hubdle.extensions.config.documentation.changelog._internal

internal fun Changelog.merged(): Changelog {
    val versionsMap: Map<Changelog.Version, List<Changelog.Version>> = buildMap {
        for (version in versions) {
            when {
                version.isFinal -> put(version, getOrDefault(version, emptyList()))
                !version.isFinal && version.hasFinal(keys) -> {
                    val versionKey = keys.versionKey(version)
                    versionKey?.let {
                        put(versionKey, getOrDefault(versionKey, emptyList()) + listOf(version))
                    }
                }
                else -> {
                    put(version, emptyList())
                }
            }
        }
    }

    val versions: Array<Changelog.Version> =
        versionsMap
            .map { (version, subVersions) ->
                val groups: List<Changelog.Version.Group> =
                    (listOf(version) + subVersions).map { it.groups.toList() }.flatten()

                val groupedGroups = groups.groupBy { it.value }

                val mergedGroups: Array<Changelog.Version.Group> =
                    groupedGroups
                        .mapNotNull { (key, groups) ->
                            val items: Array<out Changelog.Version.Group.Item> =
                                groups
                                    .filter { it.value == key }
                                    .flatMap { it.items.toList() }
                                    .toTypedArray()
                            when {
                                version.unreleased -> Changelog.Version.Group(key, *items)
                                items.isNotEmpty() -> Changelog.Version.Group(key, *items)
                                else -> null
                            }
                        }
                        .toTypedArray()

                Changelog.Version(version.value, *mergedGroups)
            }
            .toTypedArray()

    return Changelog(header, *versions)
}

private fun Changelog.Version.extractVersionWithoutStage(): String =
    extractVersion().takeWhile { it.isDigit() || it == '.' }

private fun Changelog.Version.extractVersion(): String =
    value.substringAfter("[").substringBefore("]")

private val Changelog.Version.unreleased: Boolean
    get() = extractVersion().contains("Unreleased", ignoreCase = true)

private val Changelog.Version.isFinal: Boolean
    get() = extractVersion().all { it.isDigit() || it == '.' }

private fun Changelog.Version.hasFinal(finalVersions: Iterable<Changelog.Version>): Boolean =
    finalVersions.any { it.extractVersion() == extractVersionWithoutStage() }

private fun Iterable<Changelog.Version>.versionKey(version: Changelog.Version): Changelog.Version? =
    firstOrNull {
        it.value.contains(version.extractVersionWithoutStage())
    }
