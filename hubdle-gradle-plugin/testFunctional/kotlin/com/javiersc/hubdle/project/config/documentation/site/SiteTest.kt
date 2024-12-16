package com.javiersc.hubdle.project.config.documentation.site

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import io.kotest.matchers.file.shouldBeADirectory
import io.kotest.matchers.file.shouldBeAFile
import io.kotest.matchers.file.shouldHaveSameStructureAndContentAs
import java.io.File
import kotlin.test.Test

internal class SiteTest : GradleTestKitTest() {

    private val siteDir: File = File("config/documentation/site")

    @Test
    fun `site docs snapshot 1`() {
        testSite(siteDir.resolve("snapshot-1"))
    }

    @Test
    fun `site docs snapshot 2`() {
        testSite(siteDir.resolve("snapshot-2"))
    }

    private fun testSite(sandbox: File) {
        gradleTestKitTest(sandboxPath = sandbox.path) {
            gradlew(
                "buildSite",
                "--no-scan",
                "-Porg.jetbrains.dokka.experimental.gradle.pluginMode=V2Enabled",
            )

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
