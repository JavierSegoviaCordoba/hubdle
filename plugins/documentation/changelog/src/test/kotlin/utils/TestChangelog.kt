package com.javiersc.gradle.plugins.changelog.utils

import io.kotest.matchers.file.shouldHaveSameContentAs
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import org.gradle.testkit.runner.BuildResult

@Suppress("UNUSED_PARAMETER")
internal fun testChangelog(result: BuildResult, testProjectDir: File) {
    with(testProjectDir) {
        updateChangelogActualDate()
        changelog.shouldHaveSameContentAs(changelogActual)
    }
}

internal val File.changelog: File
    get() = File("$this/CHANGELOG.md")

internal val File.changelogActual: File
    get() = File("$this/CHANGELOG_ACTUAL.md")

internal fun File.updateChangelogActualDate() {
    changelogActual.writeText(
        changelogActual
            .readText()
            .replace("{{ PLACEHOLDER_DATE }}", SimpleDateFormat("yyyy-MM-dd").format(Date()))
    )
}
