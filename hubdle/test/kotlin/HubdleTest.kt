package com.javiersc.gradle.plugins.hubdle

import com.javiersc.gradle.testkit.extensions.gradleTestKitTest
import kotlin.test.Test

class HubdleTest {

    @Test
    fun `hubdle simple`() {
        gradleTestKitTest("hubdle-simple", withDebug = false) {
            println(withArguments("assemble").build().output)
        }
    }
}
