package com.javiersc.hubdle.project.config.documentation.changelog

import com.javiersc.hubdle.project._utils.resourceFile
import com.javiersc.hubdle.project.extensions.config.documentation.changelog._internal.Changelog
import com.javiersc.hubdle.project.extensions.config.documentation.changelog._internal.fromString
import com.javiersc.hubdle.project.extensions.config.documentation.changelog._internal.merged
import io.kotest.matchers.shouldBe
import kotlin.test.Test

internal class ChangelogMergedTest {

    @Test
    fun `changelog merged 1`() {
        assert(1)
    }
}

private fun assert(number: Int) {
    val actualChangelog: Changelog = Changelog.fromString(getChangelog(number))
    val expectChangelog: Changelog = Changelog.fromString(expectChangelog(number))

    "${actualChangelog.merged()}" shouldBe "$expectChangelog"
}

private const val BASE_PATH = "config/documentation/changelog"

private fun getChangelog(number: Int): String =
    resourceFile("$BASE_PATH/changelog/merge-$number/CHANGELOG.md").readText()

private fun expectChangelog(number: Int): String =
    resourceFile("$BASE_PATH/changelog/merge-$number/CHANGELOG_EXPECT.md").readText()
