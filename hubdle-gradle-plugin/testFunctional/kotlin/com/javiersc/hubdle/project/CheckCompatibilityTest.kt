package com.javiersc.hubdle.project

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import io.kotest.matchers.string.shouldContain
import kotlin.test.Test

class CheckCompatibilityTest : GradleTestKitTest() {

    @Test
    fun `check compatibility`() {
        gradleTestKitTest("check-compatibility/1") {
            withArguments("build", "--no-scan")

            val expectOutput =
                """ 
                    |There is more than one `kotlin` project enabled:
                    |  - Android(isEnabled = true)
                    |    - Application(isEnabled = false)
                    |    - Library(isEnabled = true)
                    |  - Jvm(isEnabled = true)
                    |  - Multiplatform(isEnabled = false)
                """
                    .trimMargin()

            buildAndFail().output.shouldContain(expectOutput)
        }
    }
}
