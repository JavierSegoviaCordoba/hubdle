package com.javiersc.hubdle.config.documentation.changelog.utils

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

internal fun File.testChangelog() {
    with(this) {
        updateChangelogActualDate()
        changelog.readText().shouldNotBeBlank()
        changelogActual.readText().shouldNotBeBlank()
        changelog.readText().shouldBe(changelogActual.readText())
    }
}

internal val File.changelog: File
    get() = resolve("CHANGELOG.md")

internal val File.changelogActual: File
    get() = resolve("CHANGELOG_EXPECT.md")

internal fun File.updateChangelogActualDate() {
    changelogActual.writeText(
        changelogActual
            .readText()
            .replace("{{ PLACEHOLDER_DATE }}", SimpleDateFormat("yyyy-MM-dd").format(Date()))
    )
}
