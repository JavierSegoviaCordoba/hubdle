package com.javiersc.gradle.plugins.readme.badges.generator

import com.javiersc.gradle.plugins.core.test.arguments
import com.javiersc.gradle.plugins.core.test.getResource
import com.javiersc.gradle.plugins.core.test.taskFromArguments
import com.javiersc.gradle.plugins.core.test.testSandbox
import io.kotest.matchers.file.shouldHaveSameStructureAndContentAs
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.io.File
import kotlin.test.Test
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.TaskOutcome

class ReadmeBadgesGeneratorTest {

    @Test
    fun `build readme badges`() {
        val readmeDir: File = getResource("readme")
        val sandboxDirs: List<File> =
            checkNotNull(readmeDir.listFiles()).toList().filterNot {
                it.isFile || (it.isDirectory && it.name.endsWith("-actual"))
            }

        for (dir in sandboxDirs) {
            val sandboxPath = dir.toRelativeString(readmeDir.parentFile).replace("\\", "/")
            val actualPath = "$sandboxPath-actual/README.md"
            testSandbox(
                sandboxPath = sandboxPath,
                test = { _: BuildResult, testProjectDir: File ->
                    val expect = File("$testProjectDir/README.md")
                    val actual: File = getResource(actualPath)

                    expect shouldHaveSameStructureAndContentAs actual
                }
            )
        }
    }

    @Test
    fun `build readme badges build cache snapshot 1`() {
        val (runner, testProjectDir) =
            testSandbox(
                sandboxPath = "gradle-features/build-cache-snapshot-1",
                isBuildCacheTest = true,
                test = { result: BuildResult, testProjectDir: File ->
                    result
                        .task(":${testProjectDir.taskFromArguments}")
                        .shouldNotBeNull()
                        .outcome.shouldBe(TaskOutcome.SUCCESS)
                }
            )

        runner.withArguments(testProjectDir.arguments).build()
        val result = runner.withArguments(testProjectDir.arguments).build()
        result
            .task(":${testProjectDir.taskFromArguments}")
            .shouldNotBeNull()
            .outcome.shouldBe(TaskOutcome.SUCCESS)
    }

    @Test
    fun `build readme badges configuration cache snapshot 1`() {
        val (runner, testProjectDir) =
            testSandbox(
                sandboxPath = "gradle-features/configuration-cache-snapshot-1",
                test = { result: BuildResult, testProjectDir: File ->
                    result
                        .task(":${testProjectDir.taskFromArguments}")
                        .shouldNotBeNull()
                        .outcome.shouldBe(TaskOutcome.SUCCESS)
                }
            )

        val result = runner.withArguments(testProjectDir.arguments + "--stacktrace").build()
        println(result.output)
        result.output.shouldContain("Reusing configuration cache")
        result.task(":${testProjectDir.taskFromArguments}")?.outcome.shouldBe(TaskOutcome.SUCCESS)
    }
}
