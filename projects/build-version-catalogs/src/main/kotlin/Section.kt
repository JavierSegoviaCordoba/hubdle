@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.plugins.build.version.catalogs

internal enum class Section(val value: String) {
    METADATA("[metadata]"),
    VERSIONS("[versions]"),
    LIBRARIES("[libraries]"),
    BUNDLES("[bundles]"),
    PLUGINS("[plugins]");

    val commentedValue: String = "// $value"
}
