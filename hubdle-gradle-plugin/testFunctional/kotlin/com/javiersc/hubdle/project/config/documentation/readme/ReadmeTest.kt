package com.javiersc.hubdle.project.config.documentation.readme

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import io.kotest.matchers.shouldBe
import java.io.File
import kotlin.test.Test

internal class ReadmeTest : GradleTestKitTest() {

    @Test
    fun `readme badges`() {
        val rootDir: File = resourceFile("config")
        val readmeDir: File = resourceFile("config/documentation/readme")
        val sandboxDirs: List<File> = readmeDir.listFiles().orEmpty().toList()

        for (sandbox in sandboxDirs) {
            gradleTestKitTest(sandboxPath = sandbox.relativeTo(rootDir.parentFile).path) {
                withArgumentsFromTXT()
                val output = build().output
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
                expect.readText().shouldBe(actual.readText())
            }
        }
    }
}
