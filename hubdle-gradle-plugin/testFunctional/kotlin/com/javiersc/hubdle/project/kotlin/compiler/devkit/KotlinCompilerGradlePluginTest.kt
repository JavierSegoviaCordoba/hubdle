package com.javiersc.hubdle.project.kotlin.compiler.devkit

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldMatch
import org.junit.jupiter.api.Test

internal class KotlinCompilerGradlePluginTest : GradleTestKitTest() {

    private val basePath = "kotlin/compiler/devkit/gradle-plugin"

    @Test
    fun `DevKit Gradle plugin functional tests are in the Hubdle layout`() {
        gradleTestKitTest(basePath) {
            val output: String = gradlew(":example-gradle-plugin:help", "--no-scan").output
            output.shouldContain("BUILD SUCCESSFUL")

            val sourceSets: List<String> =
                output.lines().map(String::trim).filter { it.startsWith("SOURCE_SET ") }
            sourceSets.shouldContain(
                "SOURCE_SET functionalTest " +
                    "kotlin=[build/generated/sources/buildConfig/functionalTest, " +
                    "build/generated/testFunctional/kotlin, testFunctional/kotlin] " +
                    "resources=[build/generated/testFunctional/resources, testFunctional/resources]"
            )
            sourceSets.filter { it.contains("functionalTest/") }.shouldBeEmpty()
        }
    }

    @Test
    fun `DevKit Gradle plugin runs the functional tests against every Kotlin version`() {
        gradleTestKitTest(basePath) {
            // It compiles with the default JVM version, Java 17, which the DevKit runtime needs
            val output: String =
                gradlew(":example-gradle-plugin:allVersionedFunctionalTests", "--no-scan").output
            output.shouldContain("BUILD SUCCESSFUL")

            // The compiler plugin is published for the functional tests
            output.shouldContain("Task :example-compiler:installForFunctionalTest")
            output.shouldMatch(
                Regex("""(?s).*Task :example-gradle-plugin:k\d\w*FunctionalTest.*""")
            )

            projectDir
                .resolve("example-gradle-plugin/build/test-results")
                .walkTopDown()
                .filter { it.name == "TEST-com.kotlin.devkit.ExampleFunctionalTest.xml" }
                .toList()
                .shouldNotBeEmpty()
        }
    }

    @Test
    fun `DevKit Gradle plugin fails with the Hubdle functional tests`() {
        gradleTestKitTest(basePath) {
            withArguments(":example-gradle-plugin:help", "-PwithTestFunctional", "--no-scan")
                .buildAndFail()
                .output
                .shouldContain("The Kotlin Compiler Plugin DevKit already creates the functional")
        }
    }
}
