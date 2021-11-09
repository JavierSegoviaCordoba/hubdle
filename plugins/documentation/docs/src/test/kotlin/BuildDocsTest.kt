package com.javiersc.gradle.plugins.docs

import com.javiersc.gradle.plugins.core.test.getResource
import com.javiersc.gradle.plugins.core.test.testSandbox
import io.kotest.matchers.collections.shouldBeSameSizeAs
import io.kotest.matchers.file.shouldBeADirectory
import io.kotest.matchers.file.shouldHaveSameContentAs
import java.io.File
import kotlin.test.Test
import org.gradle.testkit.runner.BuildResult

class BuildDocsTest {

    @Test
    fun `build docs snapshot 1`() = testSandbox(sandboxPath = "docs/snapshot-1", test = ::testDocs)
}

@Suppress("UNUSED_PARAMETER")
fun testDocs(result: BuildResult, testProjectDir: File) {
    val expect = File("$testProjectDir/build/.docs/")
    val actual: File = getResource("docs/snapshot-1-actual/.docs")

    // TODO: replace with `shouldHaveSameStructureAndContentAs` when Kotest 5.0.0 is released
    val expectFiles: List<File> = expect.walkTopDown().toList()
    val actualFiles: List<File> = actual.walkTopDown().toList()

    expectFiles shouldBeSameSizeAs actualFiles

    expectFiles.zip(actualFiles).forEach { (expect, actual) ->
        when {
            expect.isDirectory -> actual.shouldBeADirectory()
            expect.isFile -> expect.shouldHaveSameContentAs(actual)
            else -> error("Unexpected error analyzing file trees")
        }
    }
}
