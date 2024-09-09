@file:Suppress("PackageDirectoryMismatch")

package com.kotlin.jvm.sandbox.project.library

import kotlin.test.Test

class BTest {

    @Test
    fun bTest() {
        require(b() == "B")
    }
}
