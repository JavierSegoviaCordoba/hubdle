package com.kotlin.devkit

import kotlin.test.Test
import kotlin.test.assertNotNull

class ExampleFunctionalTest {

    @Test
    fun `the DevKit runs the functional tests against a Kotlin version`() {
        val kotlinVersion = System.getProperty("kotlin.compiler.plugin.devkit.test.kotlin-version")
        assertNotNull(kotlinVersion)
        println("FUNCTIONAL_TEST_KOTLIN_VERSION $kotlinVersion")
    }
}
