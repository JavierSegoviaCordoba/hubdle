package com.javiersc.hubdle

import com.javiersc.gradle.testkit.test.extensions.GradleTest
import io.kotest.matchers.string.shouldContain
import kotlin.test.Test

class CheckCompatibilityTest : GradleTest() {

    @Test
    fun `check compatibility`() {
        gradleTestKitTest("check-compatibility/1", withDebug = true) {
            withArguments("build")

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
