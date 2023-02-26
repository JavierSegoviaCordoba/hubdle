package com.javiersc.hubdle.project.kotlin.jvm.features

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test

internal class KotlinJvmMoleculeFeatureTest : GradleTestKitTest() {

    @Test
    fun `molecule feature`() {
        gradleTestKitTest("kotlin/jvm/features/molecule") {
            withArguments("run").build().outputTrimmed.apply {
                shouldContain("FooState(name=Unknown, counter=0)")
                shouldContain("FooState(name=A")
                shouldContain("FooState(name=B, counter=4)")
            }
        }
    }
}
