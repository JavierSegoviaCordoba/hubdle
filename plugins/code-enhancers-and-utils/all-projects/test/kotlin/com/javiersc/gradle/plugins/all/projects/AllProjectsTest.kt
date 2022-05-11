package com.javiersc.gradle.plugins.all.projects

import com.javiersc.gradle.plugins.core.test.arguments
import com.javiersc.gradle.plugins.core.test.taskFromArguments
import com.javiersc.gradle.plugins.core.test.testSandbox
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.io.File
import kotlin.test.Test
import org.gradle.testkit.runner.TaskOutcome

class AllProjectsTest {

    @Test
    fun `all projects all pre-commits enabled 1`() {
        testSandbox(
            sandboxPath = "sandbox-all-projects-all-pre-commits-enabled-1",
            test = { _, testProjectDir ->
                testProjectDir.preCommitFileLines.shouldContain("./gradlew allTests")
                testProjectDir.preCommitFileLines.shouldContain("./gradlew apiCheck")
                testProjectDir.preCommitFileLines.shouldContain("./gradlew assemble")
                testProjectDir.preCommitFileLines.shouldContain("./gradlew spotlessCheck")
            },
        )
    }

    @Test
    fun `all projects all-tests pre-commit enabled 1 build cache`() {
        val (runner, testProjectDir) =
            testSandbox(
                sandboxPath = "sandbox-all-projects-all-tests-pre-commit-enabled-build-cache-1",
                isBuildCacheTest = true,
                test = { _, testProjectDir ->
                    testProjectDir.preCommitFileLines.shouldContain("./gradlew allTests")
                },
            )
        runner.withArguments("clean").build()
        val result = runner.withArguments(testProjectDir.arguments).build()
        result
            .task(":${testProjectDir.taskFromArguments}")
            ?.outcome.shouldBe(TaskOutcome.FROM_CACHE)
    }

    @Test
    fun `all projects all pre-commits enabled 1 configuration cache`() {
        val (runner, testProjectDir) =
            testSandbox(
                sandboxPath = "sandbox-all-projects-all-pre-commits-enabled-configuration-cache-1",
                test = { _, testProjectDir ->
                    testProjectDir.preCommitFileLines.shouldContain("./gradlew allTests")
                    testProjectDir.preCommitFileLines.shouldContain("./gradlew apiCheck")
                    testProjectDir.preCommitFileLines.shouldContain("./gradlew assemble")
                    testProjectDir.preCommitFileLines.shouldContain("./gradlew spotlessCheck")
                },
            )
        val result = runner.withArguments(testProjectDir.arguments).build()
        println(result.output)
        result.output.shouldContain("Reusing configuration cache")
        result
            .task(":${testProjectDir.taskFromArguments}")
            ?.outcome.shouldBe(TaskOutcome.UP_TO_DATE)
    }

    @Test
    fun `all projects all pre-commits enabled except apiCheck 1`() {
        testSandbox(
            sandboxPath = "sandbox-all-projects-all-pre-commits-enabled-except-api-check-1",
            test = { _, testProjectDir ->
                testProjectDir.preCommitFileLines.shouldContain("./gradlew allTests")
                testProjectDir.preCommitFileLines.shouldContain("./gradlew assemble")
                testProjectDir.preCommitFileLines.shouldContain("./gradlew spotlessCheck")
                testProjectDir.preCommitFileLines.shouldNotContain("./gradlew apiCheck")
            },
        )
    }
}

private val File.preCommitFileLines: List<String>
    get() = File("$this/.git/hooks/pre-commit").readLines()
