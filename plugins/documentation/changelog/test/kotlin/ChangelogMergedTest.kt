package com.javiersc.gradle.plugins.changelog

import com.javiersc.gradle.plugins.changelog.internal.Changelog
import com.javiersc.gradle.plugins.changelog.internal.fromString
import com.javiersc.gradle.plugins.changelog.internal.merged
import com.javiersc.gradle.plugins.core.test.getResource
import io.kotest.matchers.shouldBe
import kotlin.test.Test

internal class ChangelogMergedTest {

    @Test fun `changelog merged 1`() = assert(1)
}

private fun assert(number: Int) {
    val actualChangelog: Changelog = Changelog.fromString(getChangelog(number))
    val expectChangelog: Changelog = Changelog.fromString(expectChangelog(number))

    "${actualChangelog.merged()}" shouldBe "$expectChangelog"
}

private fun getChangelog(number: Int): String =
    getResource("changelog/merge-$number/CHANGELOG.md").readText()

private fun expectChangelog(number: Int): String =
    getResource("changelog/merge-$number/CHANGELOG_EXPECT.md").readText()
