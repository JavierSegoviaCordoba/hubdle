@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.plugins.build.version.catalogs

internal class Libraries(private val rawData: String) {

    val data: String
        get() = build(rawData)

    @Suppress("MaxLineLength")
    /**
     * Sample:
     *
     * ```
     * // [libraries]
     * val jetbrains_kotlin_kotlinTest = "org.jetbrains.kotlin:kotlin-test"
     * val jetbrains_kotlinx_kotlinxCoroutinesCore =
     *      "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines"
     * ```
     *
     * should create:
     *
     * ```
     * [libraries]
     * jetbrains-kotlin-kotlinTest = { module = "org.jetbrains.kotlin:kotlin-test" }
     * jetbrains-kotlinx-kotlinxCoroutinesCore = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines" }
     * ```
     */
    @OptIn(ExperimentalStdlibApi::class)
    private fun build(rawData: String): String {
        val section = Section.LIBRARIES

        val librariesData = extractSection(rawData, section).filter(String::isNotBlank)

        if (librariesData.isEmpty()) return ""

        val finishedLibrariesData: List<String> = buildList {
            librariesData.forEachIndexed { index, line ->
                val cleanLine = line.dropWhile(Char::isWhitespace).dropLastWhile(Char::isWhitespace)
                when {
                    cleanLine.contains("val ") && cleanLine.contains("\"").not() -> {
                        val joinCleanLineWithNextLine =
                            cleanLine +
                                librariesData[index + 1]
                                    .dropWhile(Char::isWhitespace)
                                    .dropLastWhile(Char::isWhitespace)
                        add(extractLibrary(joinCleanLineWithNextLine))
                    }
                    cleanLine.contains("val ") && cleanLine.contains("\"") -> {
                        add(extractLibrary(cleanLine))
                    }
                }
            }
        }

        return """
                  |${section.value}
                  |${finishedLibrariesData.joinToString("\n")}
               """.trimMargin()
    }
}

private fun extractLibrary(line: String): String {
    val alias =
        line.split("=")[0].replace("val ", "").filterNot(Char::isWhitespace).replace("_", "-")
    val module =
        line.split("=")[1]
            .filterNot(Char::isWhitespace)
            .removeSurrounding("\"")
            .split(":")
            .take(2)
            .joinToString(":")
    val version =
        line.split("=")[1]
            .filterNot(Char::isWhitespace)
            .removeSurrounding("\"")
            .split(":")
            .getOrNull(2)
            .run {
                when {
                    this == null -> null
                    this.contains('$').not() -> """version = "$this""""
                    this.contains('$') -> """version.ref = "${this.filterNot { it == '$' }}""""
                    else -> error("Malformed version in line: $line")
                }
            }

    return if (version == null) {
        """$alias = { module = "$module" }"""
    } else {
        """$alias = { module = "$module", $version }"""
    }
}
