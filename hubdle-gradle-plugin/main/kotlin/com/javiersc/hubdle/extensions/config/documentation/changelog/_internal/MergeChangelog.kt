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

    val versions: List<Changelog.Version> =
        versionsMap.map { (version, subVersions) ->
            val groups: List<Changelog.Version.Group> =
                (listOf(version) + subVersions).map { it.groups.toList() }.flatten()

            val groupedGroups = groups.groupBy { it.value }

            val mergedGroups: List<Changelog.Version.Group> =
                groupedGroups.mapNotNull { (key, groups) ->
                    val items: List<Changelog.Version.Group.Item> =
                        groups.filter { it.value == key }.flatMap { it.items.toList() }

                    when {
                        version.unreleased -> Changelog.Version.Group(key, items)
                        items.isNotEmpty() -> Changelog.Version.Group(key, items)
                        else -> null
                    }
                }

            Changelog.Version(version.value, mergedGroups)
        }

    val references: List<Changelog.Reference> =
        references.filter { it.version in versions.map(Changelog.Version::version) }

    return Changelog(header, versions, references)
}

private fun Changelog.Version.extractVersionWithoutStage(): String =
    version.takeWhile { it.isDigit() || it == '.' }

private val Changelog.Version.unreleased: Boolean
    get() = version.contains("Unreleased", ignoreCase = true)

private val Changelog.Version.isFinal: Boolean
    get() = version.all { it.isDigit() || it == '.' }

private fun Changelog.Version.hasFinal(finalVersions: Iterable<Changelog.Version>): Boolean =
    finalVersions.any { it.version == extractVersionWithoutStage() }

private fun Iterable<Changelog.Version>.versionKey(version: Changelog.Version): Changelog.Version? =
    firstOrNull {
        it.value.contains(version.extractVersionWithoutStage())
    }
