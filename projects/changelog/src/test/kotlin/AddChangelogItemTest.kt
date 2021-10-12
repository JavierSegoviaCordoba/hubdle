package com.javiersc.gradle.plugins.changelog

import com.javiersc.gradle.plugins.changelog.utils.arguments
import com.javiersc.gradle.plugins.changelog.utils.changelog
import com.javiersc.gradle.plugins.changelog.utils.changelogActual
import com.javiersc.gradle.plugins.changelog.utils.copyResourceTo
import com.javiersc.gradle.plugins.changelog.utils.createSandboxFile
import io.kotest.matchers.file.shouldHaveSameContentAs
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import java.io.File
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Test

class AddChangelogItemTest {

    @Test fun `added 1`() = testSandbox("sandbox-added-1")

    @Test fun `changed 1`() = testSandbox("sandbox-changed-1")

    @Test fun `deprecated 1`() = testSandbox("sandbox-deprecated-1")

    @Test fun `removed 1`() = testSandbox("sandbox-removed-1")

    @Test fun `fixed 1`() = testSandbox("sandbox-fixed-1")

    @Test fun `updated 1`() = testSandbox("sandbox-updated-1")

    @Test fun `renovate 1`() = testSandbox("sandbox-renovate-1")

    @Test fun `renovate 2`() = testSandbox("sandbox-renovate-2")
}

private fun testSandbox(name: String) {
    val testProjectDir: File = createSandboxFile(name)
    "add-changelog-item/$name" copyResourceTo testProjectDir

    with(testProjectDir) {
        val result =
            GradleRunner.create()
                .withProjectDir(this)
                .withArguments(arguments)
                .withPluginClasspath()
                .build()

        result.task(":addChangelogItem").shouldNotBeNull().outcome shouldBe TaskOutcome.SUCCESS

        changelog.shouldHaveSameContentAs(changelogActual)
    }
}
