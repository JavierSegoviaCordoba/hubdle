package com.javiersc.hubdle

import com.javiersc.gradle.testkit.test.extensions.GradleTest
import io.kotest.matchers.string.shouldContain
import kotlin.test.Test

class CheckCompatibilityTest : GradleTest() {

    @Test
    fun `check compatibility`() {
        gradleTestKitTest("check-compatibility/1") {
            withArguments("build")

            val expectOutput =
                """ 
                    |There is more than one `kotlin` project enabled:
                    |  - Android library(isEnabled = false)
                    |  - Gradle Plugin(isEnabled = false)
                    |  - Version Catalog(isEnabled = true)
                    |  - Jvm(isEnabled = true)
                    |  - Multiplatform(isEnabled = false)
                """
                    .trimMargin()

            buildAndFail().output.shouldContain(expectOutput)
        }
    }
}
