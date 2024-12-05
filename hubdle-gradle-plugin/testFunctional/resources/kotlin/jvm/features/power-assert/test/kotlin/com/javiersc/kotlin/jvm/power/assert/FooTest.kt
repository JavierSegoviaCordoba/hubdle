package com.javiersc.kotlin.jvm.power.assert

import kotlin.test.assertTrue
import kotlin.test.Test

class FooTest {

    @Test
    fun test() {
        val foo = "FOO"
        val bar = "BAR"
        assertTrue(foo == bar)
    }
}
