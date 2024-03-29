package com.javiersc.hubdle.project.config.documentation.site

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import io.kotest.matchers.file.shouldBeADirectory
import io.kotest.matchers.file.shouldBeAFile
import io.kotest.matchers.file.shouldHaveSameStructureAndContentAs
import java.io.File
import kotlin.test.Test

internal class SiteTest : GradleTestKitTest() {

    @Test
    fun `site docs`() {
        val rootDir: File = resourceFile("config")
        val readmeDir: File = resourceFile("config/documentation/site")
        val sandboxDirs: List<File> = readmeDir.listFiles().orEmpty().toList()

        for (sandbox in sandboxDirs) {
            gradleTestKitTest(sandboxPath = sandbox.relativeTo(rootDir.parentFile).path) {
                withArgumentsFromTXT()
                build()

                val expect: File = projectDir.resolve(".docs.expect")
                val actual: File = projectDir.resolve("build/.docs/")

                expect shouldHaveSameStructureAndContentAs actual

                projectDir.resolve("build/.docs/").shouldBeADirectory()
                projectDir.resolve("build/docs/").shouldBeADirectory()

                val siteDir = projectDir.resolve("build/docs/_site/")

                siteDir.resolve("index.html").shouldBeAFile()
                siteDir.resolve("api/").shouldBeADirectory()

                if (sandbox.name.contains("snapshot")) {
                    siteDir.resolve("api/snapshot/").shouldBeADirectory()
                } else {
                    siteDir.resolve("api/versions/").shouldBeADirectory()
                }
            }
        }
    }
}
