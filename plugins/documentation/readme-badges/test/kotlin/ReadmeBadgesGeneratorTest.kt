package com.javiersc.gradle.plugins.readme.badges.generator

import com.javiersc.gradle.plugins.core.test.getResource
import com.javiersc.gradle.plugins.core.test.testSandbox
import io.kotest.matchers.file.shouldHaveSameStructureAndContentAs
import java.io.File
import kotlin.test.Test
import org.gradle.testkit.runner.BuildResult

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
}
