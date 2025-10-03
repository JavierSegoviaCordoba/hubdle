package com.javiersc.hubdle.project.kotlin.jvm.features

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test

internal class KotlinCompilerPluginFeatureTest : GradleTestKitTest() {

    @Test
    fun `running deleteCompilerTextTestFiles task deletes the txt files`() {
        gradleTestKitTest("kotlin/jvm/features/compiler-plugin") {
            val output: String = gradlew("deleteCompilerTextTestFiles", "--no-scan").output
            output.shouldContain("BUILD SUCCESSFUL")
            projectDir
                .resolve("test-data")
                .walkTopDown()
                .filter { it.isFile }
                .map { it.name }
                .sorted()
                .toList()
                .shouldHaveSize(5)
                .shouldBe(
                    listOf(
                        "DISABLED.fir.txt",
                        "TODO.fir.ir.txt",
                        "disabled.fir.ir.txt",
                        "test.kt",
                        "todo.fir.txt",
                    )
                )
        }
    }
}
