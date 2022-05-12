package com.javiersc.gradle.plugins.docs

import com.javiersc.gradle.plugins.core.test.arguments
import com.javiersc.gradle.plugins.core.test.getResource
import com.javiersc.gradle.plugins.core.test.taskFromArguments
import com.javiersc.gradle.plugins.core.test.testSandbox
import io.kotest.matchers.file.shouldBeADirectory
import io.kotest.matchers.file.shouldBeAFile
import io.kotest.matchers.file.shouldHaveSameStructureAndContentAs
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import java.io.File
import kotlin.test.Ignore
import kotlin.test.Test
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.TaskOutcome

class BuildDocsTest {

    @Test
    fun `build docs`() {
        val docsDir: File = getResource("docs")
        val sandboxDirs: List<File> =
            checkNotNull(docsDir.listFiles()).toList().filterNot {
                it.isFile || (it.isDirectory && it.name.endsWith("-actual"))
            }

        for (dir in sandboxDirs) {
            val sandboxPath = dir.toRelativeString(docsDir.parentFile).replace("\\", "/")
            val actualPath = "$sandboxPath-actual/.docs"
            testSandbox(
                sandboxPath = sandboxPath,
                test = { _: BuildResult, testProjectDir: File ->
                    val expect = File("$testProjectDir/build/.docs/")
                    val actual: File = getResource(actualPath)

                    expect shouldHaveSameStructureAndContentAs actual

                    File("$testProjectDir/build/.docs/").shouldBeADirectory()
                    File("$testProjectDir/build/docs/").shouldBeADirectory()

                    val siteDir = File("$testProjectDir/build/docs/_site/")

                    File("$siteDir/index.html").shouldBeAFile()
                    File("$siteDir/api/").shouldBeADirectory()

                    if (sandboxPath.contains("snapshot")) {
                        File("$siteDir/api/snapshot/").shouldBeADirectory()
                    } else {
                        File("$siteDir/api/versions/").shouldBeADirectory()
                    }
                }
            )
        }
    }

    @Test
    fun `build cache docs`() {
        // to simulate IDEA sync the task is run three times
        val (runner, testProjectDir) =
            testSandbox(
                sandboxPath = "docs-gradle-features/build-cache-1",
                isBuildCacheTest = true,
                test = { result: BuildResult, testProjectDir: File ->
                    result
                        .task(":${testProjectDir.taskFromArguments}")
                        .shouldNotBeNull()
                        .outcome.shouldBe(TaskOutcome.SUCCESS)
                }
            )

        File("$testProjectDir/build").deleteRecursively()
        runner.withArguments(testProjectDir.arguments).build()
        File("$testProjectDir/build").deleteRecursively()
        val result = runner.withArguments(testProjectDir.arguments).build()
        result
            .task(":${testProjectDir.taskFromArguments}")
            .shouldNotBeNull()
            .outcome.shouldBe(TaskOutcome.FROM_CACHE)
    }

    @Ignore("MkDocs Gradle plugin needs to be compatible with Configuration cache (grgit issue)")
    @Test
    fun `configuration cache docs`() {
        val (runner, testProjectDir) =
            testSandbox(
                sandboxPath = "docs-gradle-features/configuration-cache-1",
                test = { result: BuildResult, testProjectDir: File ->
                    result
                        .task(":${testProjectDir.taskFromArguments}")
                        .shouldNotBeNull()
                        .outcome.shouldBe(TaskOutcome.SUCCESS)
                }
            )

        val result = runner.withArguments(testProjectDir.arguments + "--info").build()
        println("----------------")
        println(result.output)
        println("----------------")
        result.output.shouldContain("Reusing configuration cache")
        result
            .task(":${testProjectDir.taskFromArguments}")
            .shouldNotBeNull()
            .outcome.shouldBe(TaskOutcome.UP_TO_DATE)
    }
}
