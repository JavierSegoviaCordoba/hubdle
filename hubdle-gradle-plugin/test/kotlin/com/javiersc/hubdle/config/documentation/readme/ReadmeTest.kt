package com.javiersc.hubdle.config.documentation.readme

import com.javiersc.gradle.testkit.test.extensions.gradleTestKitTest
import com.javiersc.gradle.testkit.test.extensions.resourceFile
import com.javiersc.gradle.testkit.test.extensions.withArgumentsFromTXT
import io.kotest.matchers.file.shouldHaveSameStructureAndContentAs
import java.io.File
import kotlin.test.Test

internal class ReadmeTest {

    @Test
    fun `readme badges`() {
        val rootDir: File = resourceFile("config")
        val readmeDir: File = resourceFile("config/documentation/readme")
        val sandboxDirs: List<File> = readmeDir.listFiles().orEmpty().toList()

        for (sandbox in sandboxDirs) {
            gradleTestKitTest(sandboxPath = sandbox.relativeTo(rootDir.parentFile).path) {
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
