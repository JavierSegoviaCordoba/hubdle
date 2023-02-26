package com.javiersc.hubdle.project.config.documentation.changelog

import com.javiersc.hubdle.project._utils.resourceFile
import com.javiersc.hubdle.project.extensions.config.documentation.changelog._internal.Changelog
import com.javiersc.hubdle.project.extensions.config.documentation.changelog._internal.fromString
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

    @Test
    fun `changelog to string 3`() {
        assert(3)
    }
}

private fun assert(number: Int) {
    val expectChangelog: String = expectChangelog(number)
    val actualChangelog: Changelog = Changelog.fromString(getChangelog(number))

    expectChangelog shouldBe "$actualChangelog"
}

private const val BASE_PATH = "config/documentation/changelog"

private fun getChangelog(number: Int): String =
    resourceFile("$BASE_PATH/changelog/$number/CHANGELOG.md").readText()

private fun expectChangelog(number: Int): String =
    resourceFile("$BASE_PATH/changelog/$number/CHANGELOG_EXPECT.md").readText()
