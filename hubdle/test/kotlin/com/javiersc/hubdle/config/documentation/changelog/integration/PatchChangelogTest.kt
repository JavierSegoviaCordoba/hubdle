package com.javiersc.hubdle.config.documentation.changelog.integration

import com.javiersc.gradle.plugins.core.test.arguments
import com.javiersc.gradle.plugins.core.test.taskFromArguments
import com.javiersc.gradle.plugins.core.test.testSandbox
import com.javiersc.gradle.testkit.extensions.gradleTestKitTest
import com.javiersc.gradle.testkit.extensions.gradlewArgumentFromTXT
import com.javiersc.hubdle.config.documentation.changelog.utils.testChangelog
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlin.test.Ignore
import kotlin.test.Test
import org.gradle.testkit.runner.TaskOutcome

class PatchChangelogTest {

    private val basePath = "config/documentation/changelog/patch-changelog"

    @Test
    fun `patch 1`() {
        gradleTestKitTest("$basePath/sandbox-patch-1") {
            val result = gradlewArgumentFromTXT()
            testChangelog(result, projectDir)
        }
    }

    // TODO: re-enable
    @Ignore
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
