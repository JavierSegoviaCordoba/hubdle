package com.javiersc.gradle.plugins.docs

import com.javiersc.gradle.plugins.core.test.getResource
import com.javiersc.gradle.plugins.core.test.testSandbox
import io.kotest.matchers.file.shouldHaveSameStructureAndContentAs
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

    expect shouldHaveSameStructureAndContentAs actual
}
