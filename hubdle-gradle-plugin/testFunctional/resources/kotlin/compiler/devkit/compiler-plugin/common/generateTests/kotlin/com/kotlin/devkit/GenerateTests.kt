package com.kotlin.devkit

import org.jetbrains.kotlin.compiler.plugin.devkit.generateDevKitTestsWithJUnit5

public fun main(): Unit = generateDevKitTestsWithJUnit5 {
    testClass<AbstractJvmBoxTest> { model("box") }
}
