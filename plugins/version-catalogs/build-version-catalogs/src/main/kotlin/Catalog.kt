@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.plugins.build.version.catalogs

internal class Catalog(private val rawData: String) {

    val data: String
        get() =
            listOf(
                    metadata.data,
                    versions.data,
                    libraries.data,
                    bundles.data,
                    plugins.data,
                )
                .filter(String::isNotBlank)
                .joinToString("\n\n") + "\n"

    val metadata: Metadata
        get() = Metadata(rawData)

    val versions: Versions
        get() = Versions(rawData)

    val libraries: Libraries
        get() = Libraries(rawData)

    val bundles: Bundles
        get() = Bundles(rawData)

    val plugins: Plugins
        get() = Plugins(rawData)
}
