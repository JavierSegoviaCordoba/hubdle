@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.plugins.build.version.catalogs

internal class Versions(private val rawData: String) {

    val data: String
        get() = build(rawData)

    @Suppress("MaxLineLength")
    /**
     * Sample:
     *
     * ```
     * // [versions]
     * val kotlin = "1.5.21"
     * ```
     *
     * should create:
     *
     * ```
     * [versions]
     * kotlin = "1.5.21"
     * ```
     */
    @OptIn(ExperimentalStdlibApi::class)
    private fun build(rawData: String): String {
        val section = Section.VERSIONS

        val versionsData = extractSection(rawData, section).filter(String::isNotBlank)

        if (versionsData.isEmpty()) return ""

        val finishedVersionsData: List<String> = buildList {
            versionsData.forEachIndexed { index, line ->
                val cleanLine = line.dropWhile(Char::isWhitespace).dropLastWhile(Char::isWhitespace)

                when {
                    cleanLine.contains("val ") && cleanLine.contains("\"").not() -> {
                        val joinCleanLineWithNextLine =
                            cleanLine +
                                versionsData[index + 1]
                                    .dropWhile(Char::isWhitespace)
                                    .dropLastWhile(Char::isWhitespace)
                        add(extractVersion(joinCleanLineWithNextLine))
                    }
                    cleanLine.contains("val ") && cleanLine.contains("\"") -> {
                        add(extractVersion(cleanLine))
                    }
                }
            }
        }

        return """
                  |${section.value}
                  |${finishedVersionsData.joinToString("\n")}
               """.trimMargin()
    }
}

private fun extractVersion(line: String): String {
    val alias = line.split("=")[0].replace("val ", "").filterNot(Char::isWhitespace)
    val version = line.split("=")[1].filterNot(Char::isWhitespace).replace("\"", "")
    return """$alias = "$version""""
}
