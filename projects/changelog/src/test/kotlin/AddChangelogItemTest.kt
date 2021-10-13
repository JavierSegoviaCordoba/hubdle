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
import org.eclipse.jgit.api.Git
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

    @Test fun `renovate 3`() = testSandbox("sandbox-renovate-3")

    @Test
    fun `renovate 4`() =
        testSandbox(
            name = "sandbox-renovate-4",
            commitMessage =
                """
                    | datasource | package                                                   | from  | to  |
                    | ---------- | --------------------------------------------------------- | ----- | --- |
                    | maven      | com.gradle.enterprise:com.gradle.enterprise.gradle.plugin | 3.6.4 | 3.7 |
                """.trimIndent()
        )

    @Test
    fun `renovate 5`() =
        testSandbox(
            name = "sandbox-renovate-5",
            commitMessage =
                """
                    | datasource | package                                                   | from  | to    |
                    | ---------- | --------------------------------------------------------- | ----- | ----- |
                    | maven      | com.gradle.enterprise:com.gradle.enterprise.gradle.plugin | 3.6.4 | 3.7   |
                    | maven      | com.javiersc.mokoki:mokoki-core                           | 1.0.0 | 1.0.1 |
                    | maven      | com.javiersc.either:either-core                           | 2.0.1 | 2.0.2 |
                """.trimIndent()
        )
}

private fun testSandbox(name: String, commitMessage: String? = null) {
    val testProjectDir: File = createSandboxFile(name)
    "add-changelog-item/$name" copyResourceTo testProjectDir

    if (commitMessage != null) {
        val git: Git = Git.init().setDirectory(testProjectDir).call()
        git.add().addFilepattern(".").call()
        git.commit().setMessage(commitMessage).call()
        git.checkout().setCreateBranch(true).setName("sandbox/changelog-items").call()
    }

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
