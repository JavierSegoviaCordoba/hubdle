package com.javiersc.hubdle.kotlin.tools

import com.javiersc.gradle.testkit.test.extensions.gradleTestKitTest
import com.javiersc.gradle.testkit.test.extensions.gradlewArgumentFromTXT
import io.kotest.matchers.file.shouldBeADirectory
import io.kotest.matchers.file.shouldBeAFile
import kotlin.test.Test

internal class AnalysisTest {

    private val basePath = "kotlin/tools/analysis"

    @Test
    fun `analysis 1`() {
        gradleTestKitTest("$basePath/1") {
            gradlewArgumentFromTXT()
            val detektDir = projectDir.resolve("build/reports/detekt")
            detektDir.shouldBeADirectory()
            detektDir.resolve("detekt.html").shouldBeAFile()
            detektDir.resolve("detekt.sarif").shouldBeAFile()
            detektDir.resolve("detekt.xml").shouldBeAFile()
        }
    }
}
