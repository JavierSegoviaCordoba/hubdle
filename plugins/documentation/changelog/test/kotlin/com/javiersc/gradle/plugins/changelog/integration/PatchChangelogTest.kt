package com.javiersc.gradle.plugins.changelog.integration

import com.javiersc.gradle.plugins.changelog.utils.testChangelog
import com.javiersc.gradle.plugins.core.test.arguments
import com.javiersc.gradle.plugins.core.test.taskFromArguments
import com.javiersc.gradle.plugins.core.test.testSandbox
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlin.test.Test
import org.gradle.testkit.runner.TaskOutcome

class PatchChangelogTest {

    @Test
    fun `patch 1`() {
        testSandbox(sandboxPath = "patch-changelog/sandbox-patch-1", test = ::testChangelog)
    }

    @Test
    fun `patch configuration cache 1`() {
        val (runner, testProjectDir) =
            testSandbox(
                sandboxPath = "patch-changelog/sandbox-patch-configuration-cache-1",
                test = ::testChangelog
            )

        val result = runner.withArguments(testProjectDir.arguments).build()
        result.output.shouldContain("Reusing configuration cache")
        result
            .task(":${testProjectDir.taskFromArguments}")
            .shouldNotBeNull()
            .outcome.shouldBe(TaskOutcome.SUCCESS)
    }
}
