package com.javiersc.hubdle.config.documentation.changelog

import com.javiersc.gradle.plugins.core.test.getResource
import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.Changelog
import com.javiersc.hubdle.extensions.config.documentation.changelog._internal.fromString
import io.kotest.matchers.shouldBe
import kotlin.test.Test

internal class ChangelogTest {

    @Test
    fun `changelog to string 1`() {
        assert(1)
    }

    @Test
    fun `changelog to string 2`() {
        assert(2)
    }
}

private fun assert(number: Int) {
    val expectChangelog: String = expectChangelog(number)
    val actualChangelog: Changelog = Changelog.fromString(getChangelog(number))

    expectChangelog shouldBe "$actualChangelog"
}

private val basePath = "config/documentation/changelog"

private fun getChangelog(number: Int): String =
    getResource("$basePath/changelog/$number/CHANGELOG.md").readText()

private fun expectChangelog(number: Int): String =
    getResource("$basePath/changelog/$number/CHANGELOG_EXPECT.md").readText()
