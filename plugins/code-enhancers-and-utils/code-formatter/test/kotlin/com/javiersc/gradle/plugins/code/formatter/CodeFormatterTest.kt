package com.javiersc.gradle.plugins.code.formatter

import com.javiersc.gradle.plugins.core.test.getResource
import com.javiersc.gradle.plugins.core.test.testSandbox
import io.kotest.matchers.collections.shouldBeSameSizeAs
import io.kotest.matchers.file.shouldBeADirectory
import io.kotest.matchers.file.shouldHaveSameContentAs
import java.io.File
import kotlin.test.Test
import org.gradle.testkit.runner.BuildResult

class CodeFormatterTest {

    @Test fun `format 1`() = testSandbox(sandboxPath = "sandbox-format-1", test = ::testFormatter)
}

@Suppress("UNUSED_PARAMETER")
fun testFormatter(result: BuildResult, testProjectDir: File) {
    val expect = File("$testProjectDir/library/")
    val actual: File = getResource("sandbox-format-1-actual/library")

    val expectFiles: List<File> =
        expect.walkTopDown().toList().filter { it.path.contains("spotless").not() }
    val actualFiles: List<File> =
        actual.walkTopDown().toList().filter { it.path.contains("spotless").not() }

    expectFiles shouldBeSameSizeAs actualFiles

    expectFiles.zip(actualFiles).forEach { (expect, actual) ->
        when {
            expect.isDirectory -> actual.shouldBeADirectory()
            expect.isFile -> expect.shouldHaveSameContentAs(actual)
            else -> error("Unexpected error analyzing file trees")
        }
    }
}
