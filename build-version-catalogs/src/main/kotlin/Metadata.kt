@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.plugins.build.version.catalogs

internal class Metadata(private val rawData: String) {

    val data: String
        get() = build(rawData)

    @Suppress("MaxLineLength")
    /**
     * Sample:
     *
     * ```
     * // [metadata]
     * val format_version = "1.1"
     * ```
     *
     * should create:
     *
     * ```
     * [metadata]
     * format.version = "1.1"
     * ```
     */
    @OptIn(ExperimentalStdlibApi::class)
    private fun build(rawData: String): String {
        val section = Section.METADATA

        val metadataData = extractSection(rawData, section).filter(String::isNotBlank)

        if (metadataData.isEmpty()) return ""

        val finishedMetadataData: List<String> = buildList {
            metadataData.forEachIndexed { index, line ->
                val cleanLine = line.dropWhile(Char::isWhitespace).dropLastWhile(Char::isWhitespace)

                when {
                    cleanLine.contains("val ") && cleanLine.contains("\"").not() -> {
                        val joinCleanLineWithNextLine =
                            cleanLine +
                                metadataData[index + 1]
                                    .dropWhile(Char::isWhitespace)
                                    .dropLastWhile(Char::isWhitespace)
                        add(extractMetadata(joinCleanLineWithNextLine))
                    }
                    cleanLine.contains("val ") && cleanLine.contains("\"") -> {
                        add(extractMetadata(cleanLine))
                    }
                }
            }
        }

        return """
                  |${section.value}
                  |${finishedMetadataData.joinToString("\n")}
               """.trimMargin()
    }
}

private fun extractMetadata(line: String): String {
    val metadataKey =
        line.split("=")[0].replace("val ", "").filterNot(Char::isWhitespace).replace("_", ".")
    val metadataValue = line.split("=")[1].filterNot(Char::isWhitespace).replace("\"", "")
    return """$metadataKey = "$metadataValue""""
}
