package com.javiersc.hubdle.project.config

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import kotlin.test.Test

internal class CoverageTest : GradleTestKitTest() {

    private val basePath = "config/coverage"

    @Test
    fun `coverage 1`() {
        gradleTestKitTest("$basePath/1", name = "sandbox-project") {
            gradlewArgumentFromTXT()
            val content: String =
                projectDir.resolve("build/reports/kover/xml/report.xml").readText()
            content.contains(
                "name=\"com/kotlin/jvm/sandbox/project/library/BKt\" sourcefilename=\"b.kt\""
            )
            content.contains("name=\"com/kotlin/jvm/sandbox/project/AKt\" sourcefilename=\"a.kt\"")
        }
    }
}
