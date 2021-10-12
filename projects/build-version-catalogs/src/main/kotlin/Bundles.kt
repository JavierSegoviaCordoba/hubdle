@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.plugins.build.version.catalogs

internal class Bundles(private val rawData: String) {

    val data: String
        get() = build(rawData)

    @Suppress("MaxLineLength")
    /**
     * Sample:
     *
     * ```
     * // [bundles]
     * val bundle_1 = jetbrains_kotlin_kotlinTest + jetbrains_kotlinx_kotlinxCoroutinesCore
     * val bundle2 = jetbrains_kotlin_kotlinTest + kotest_kotestAssertionsCore
     * ```
     *
     * should create:
     *
     * ```
     * [bundles]
     * bundle-1 = ["jetbrains-kotlin-kotlinTest", "jetbrains-kotlinx-kotlinxCoroutinesCore"]
     * bundle2 = ["jetbrains-kotlin-kotlinTest", "kotest-kotestAssertionsCore"]
     * ```
     */
    @OptIn(ExperimentalStdlibApi::class)
    private fun build(rawData: String): String {
        val section = Section.BUNDLES

        val bundlesData = extractSection(rawData, section).filter(String::isNotBlank)

        if (bundlesData.isEmpty()) return ""

        val bundlesMap = mutableMapOf<String, List<String>>()
        var lastAlias = ""

        bundlesData.forEach { line ->
            if (line.contains("val ")) lastAlias = extractAlias(line)

            val currentList = bundlesMap[lastAlias] ?: emptyList()
            bundlesMap[lastAlias] = (currentList + extractBundles(line)).filter(String::isNotBlank)
        }

        val finishedBundlesData =
            bundlesMap.map { (alias, bundles) -> "$alias = [${bundles.joinToString()}]" }

        return """
                  |${section.value}
                  |${finishedBundlesData.joinToString("\n")}
               """.trimMargin()
    }
}

private fun extractAlias(line: String): String =
    line.split("=")[0].replace("val ", "").filterNot(Char::isWhitespace).replace("_", "-")

private fun extractBundles(line: String): List<String> {
    val lineWithoutAlias = if (line.contains("val ")) line.split("=")[1] else line

    if (lineWithoutAlias.filterNot(Char::isWhitespace).contains("+").not()) {
        val bundle = lineWithoutAlias.replace("_", "-").filterNot(Char::isWhitespace)
        return if (bundle.isNotBlank()) listOf(""""$bundle"""") else emptyList()
    }

    return lineWithoutAlias
        .replace("_", "-")
        .filterNot(Char::isWhitespace)
        .split("+")
        .filter(String::isNotBlank)
        .map { bundle -> """"$bundle"""" }
}
