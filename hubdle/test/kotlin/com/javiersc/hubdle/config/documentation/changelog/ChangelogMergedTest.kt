package com.javiersc.hubdle.config.documentation.changelog

import com.javiersc.gradle.plugins.core.test.getResource
import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.Changelog
import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.fromString
import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.merged
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

private fun getChangelog(number: Int): String =
    getResource("config/documentation/changelog/merge-$number/CHANGELOG.md").readText()

private fun expectChangelog(number: Int): String =
    getResource("config/documentation/changelog/merge-$number/CHANGELOG_EXPECT.md").readText()
