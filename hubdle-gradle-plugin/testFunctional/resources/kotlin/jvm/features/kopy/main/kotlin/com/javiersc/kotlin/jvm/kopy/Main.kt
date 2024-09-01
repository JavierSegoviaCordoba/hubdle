@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.kotlin.jvm.kopy

import com.javiersc.kotlin.kopy.Kopy

fun main() {
    val a1 = A(b = B(c = "Hello world"))
    val a2 = a1.copy {
        b.c = "Hello, World!"
    }

    check(a1.b.c == "Hello world")
    check(a2.b.c == "Hello, World!")

    println(a1)
    println(a2)
}

@Kopy data class A(val b: B)
@Kopy data class B(val c: String)
