@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.plugins.build.version.catalogs

internal class Plugins(private val rawData: String) {

    val data: String
        get() = build(rawData)

    @Suppress("MaxLineLength")
    /**
     * Sample:
     *
     * ```
     * // [plugins]
     * val kotlin_multiplatform = "org.jetbrains.kotlin.multiplatform" to kotlin
     * val kotlin_serialization = "org.jetbrains.kotlin.plugin.serialization" to "1.5.21"
     * ```
     *
     * should create:
     *
     * ```
     * [plugins]
     * kotlin-multiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
     * kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version = "1.5.21" }
     * ```
     */
    @OptIn(ExperimentalStdlibApi::class)
    private fun build(rawData: String): String {
        val section = Section.PLUGINS

        val pluginsData = extractSection(rawData, section).filter(String::isNotBlank)

        if (pluginsData.isEmpty()) return ""

        val finishedPluginsData: List<String> = buildList {
            pluginsData.forEachIndexed { index, line ->
                val cleanLine = line.dropWhile(Char::isWhitespace).dropLastWhile(Char::isWhitespace)
                when {
                    cleanLine.contains("val ") && cleanLine.contains("\"").not() -> {
                        val joinCleanLineWithNextLine =
                            cleanLine +
                                pluginsData[index + 1]
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
                  |${finishedPluginsData.joinToString("\n")}
               """.trimMargin()
    }
}

private fun extractLibrary(line: String): String {
    val alias =
        line.split("=")[0].replace("val ", "").filterNot(Char::isWhitespace).replace("_", "-")
    val pluginId =
        line.split("=")[1].split("to")[0]
            .filterNot(Char::isWhitespace)
            .removeSurrounding("\"")
            .split(":")
            .take(2)
            .joinToString(":")
    val version =
        line.split("=")[1].split("to")[1]
            .filterNot(Char::isWhitespace)
            .removeSurrounding("\"")
            .run {
                when {
                    this == null -> error("[plugins] doesn't support empty versions")
                    this[0].isDigit() -> """version = "$this""""
                    else -> """version.ref = "${this.filterNot { it == '$' }}""""
                }
            }

    return """$alias = { id = "$pluginId", $version }"""
}
