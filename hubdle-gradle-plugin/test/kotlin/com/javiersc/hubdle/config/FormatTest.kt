package com.javiersc.hubdle.config

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import com.javiersc.kotlin.stdlib.fifth
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlin.test.Test

internal class FormatTest : GradleTestKitTest() {

    private val basePath = "config/format"

    @Test
    fun `apply 1`() {
        gradleTestKitTest("$basePath/apply-1") {
            gradlewArgumentFromTXT()
            projectDir
                .resolve("main/kotlin/Format.kt")
                .readLines()
                .fifth()
                .shouldBe("val hello = 1".prependIndent())
        }
    }

    @Test
    fun `check 1`() {
        gradleTestKitTest("$basePath/check-1") {
            withArgumentsFromTXT()
            buildAndFail().output.shouldContain("The following files had format violations")
        }
    }
}
