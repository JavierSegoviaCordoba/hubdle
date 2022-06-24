package com.javiersc.hubdle.config.documentation.readme

import com.javiersc.gradle.plugins.core.test.getResource
import com.javiersc.gradle.testkit.extensions.gradleTestKitTest
import com.javiersc.gradle.testkit.extensions.withArgumentsFromTXT
import io.kotest.matchers.file.shouldHaveSameStructureAndContentAs
import java.io.File
import kotlin.test.Test

class ReadmeTest {

    @Test
    fun `readme badges`() {
        val rootDir: File = getResource("config")
        val readmeDir: File = getResource("config/documentation/readme")
        val sandboxDirs: List<File> = readmeDir.listFiles().orEmpty().toList()

        for (sandbox in sandboxDirs) {
            gradleTestKitTest(
                sandboxPath = sandbox.relativeTo(rootDir.parentFile).path,
                withDebug = false
            ) {
                withArgumentsFromTXT()
                build()
                val expect: File = projectDir.resolve("README.expect.md")
                val actual: File = projectDir.resolve("README.md")

                val actualKotlinVersion =
                    actual
                        .readLines()
                        .first()
                        .substringAfter("kotlin-")
                        .substringBefore("-blueviolet")

                expect.apply {
                    val updatedText = readText().replace("{VERSION}", actualKotlinVersion)
                    writeText(updatedText)
                }
                expect shouldHaveSameStructureAndContentAs actual
            }
        }
    }
}
