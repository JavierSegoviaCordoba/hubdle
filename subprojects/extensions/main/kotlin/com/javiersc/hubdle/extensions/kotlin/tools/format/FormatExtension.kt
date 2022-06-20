package com.javiersc.hubdle.extensions.kotlin.tools.format

public abstract class FormatExtension {

    public val includes: MutableList<String> =
        mutableListOf(
            "*/kotlin/**/*.kt",
            "src/*/kotlin/**/*.kt",
        )

    public val excludes: MutableList<String> =
        mutableListOf(
            "*/resources/**/*.kt",
            "src/*/resources/**/*.kt",
            "**/build/**",
            "**/.gradle/**",
        )

    public var ktfmtVersion: String = "0.37" // TODO: codegen last version
}
