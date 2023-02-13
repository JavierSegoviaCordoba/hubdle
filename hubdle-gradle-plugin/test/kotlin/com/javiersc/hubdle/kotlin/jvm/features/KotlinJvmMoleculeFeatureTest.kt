package com.javiersc.hubdle.kotlin.jvm.features

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test

internal class KotlinJvmMoleculeFeatureTest : GradleTestKitTest() {

    @Test
    fun `molecule feature`() {
        gradleTestKitTest("kotlin/jvm/features/molecule") {
            val expected =
                """
                    FooState(name=Unknown, counter=0)
                    FooState(name=Unknown, counter=1)
                    FooState(name=A, counter=1)
                    FooState(name=A, counter=2)
                    FooState(name=A, counter=3)
                    FooState(name=B, counter=3)
                    FooState(name=B, counter=4)
                """
                    .trimIndent()
            withArguments("run").buildAndFail().outputTrimmed.shouldContain(expected)
        }
    }
}
