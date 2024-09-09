@file:Suppress("PackageDirectoryMismatch")

package com.kotlin.jvm.sandbox.project

import kotlin.test.Test

class ATest {

    @Test
    fun aTest() {
        require(a() == "A")
    }
}
