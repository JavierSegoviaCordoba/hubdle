@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.plugins.build.version.catalogs

internal fun extractSection(rawData: String, section: Section): List<String> {
    val rawDataLines: List<String> = rawData.lines().filter(String::isNotBlank)

    if (rawDataLines.none { line -> line.contains(section.commentedValue) }) return emptyList()

    val initialIndex: Int =
        rawDataLines.indexOfFirst { line -> line.contains(section.commentedValue) } + 1

    return rawDataLines.drop(initialIndex).takeWhile { line ->
        Section.values().none { section -> line.contains(section.commentedValue) }
    }
}
