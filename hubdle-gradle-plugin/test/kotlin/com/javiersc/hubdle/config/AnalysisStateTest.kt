package com.javiersc.hubdle.config

import com.javiersc.gradle.testkit.test.extensions.GradleTest
import io.kotest.matchers.file.shouldBeADirectory
import io.kotest.matchers.file.shouldBeAFile
import kotlin.test.Test

internal class AnalysisStateTest : GradleTest() {

    private val basePath = "config/analysis"

    @Test
    fun `analysis 1`() {
        gradleTestKitTest("$basePath/1") {
            gradlewArgumentFromTXT()
            val detektDir = projectDir.resolve("build/reports/detekt")
            detektDir.shouldBeADirectory()
            detektDir.resolve("detekt.md").shouldBeAFile()
            detektDir.resolve("detekt.html").shouldBeAFile()
            detektDir.resolve("detekt.sarif").shouldBeAFile()
            detektDir.resolve("detekt.xml").shouldBeAFile()
        }
    }
}
