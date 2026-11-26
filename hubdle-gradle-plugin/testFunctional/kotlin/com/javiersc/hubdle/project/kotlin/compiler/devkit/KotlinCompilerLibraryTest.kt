package com.javiersc.hubdle.project.kotlin.compiler.devkit

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test

internal class KotlinCompilerLibraryTest : GradleTestKitTest() {

    private val basePath = "kotlin/compiler/devkit"

    @Test
    fun `DevKit source sets are mapped to Hubdle layout`() {
        gradleTestKitTest("$basePath/compiler-library") {
            val output: String = gradlew("help", "--no-scan").output
            output.shouldContain("BUILD SUCCESSFUL")

            // The DevKit settings plugin is applied, so it adds its repositories
            output.shouldContain("REPOSITORY https://redirector.kotlinlang.org/maven/bootstrap")
            output.shouldContain("REPOSITORY https://redirector.kotlinlang.org/maven/dev/")

            val sourceSets: List<String> =
                output.lines().map(String::trim).filter { it.startsWith("SOURCE_SET ") }

            sourceSets.shouldContainAll(
                sourceSet(
                    name = "commonMain",
                    kotlin =
                        listOf(
                            "build/generated/common/main/kotlin",
                            "build/generated/sources/buildConfig/main",
                            "common/main/kotlin",
                        ),
                    resources =
                        listOf("build/generated/common/main/resources", "common/main/resources"),
                ),
                sourceSet(
                    name = "commonTestFixtures",
                    kotlin =
                        listOf(
                            "build/generated/common/testFixtures/kotlin",
                            "build/generated/sources/buildConfig/commonTestFixtures",
                            "common/testFixtures/kotlin",
                        ),
                    resources =
                        listOf(
                            "build/generated/common/testFixtures/resources",
                            "common/testFixtures/resources",
                        ),
                ),
                sourceSet(
                    name = "commonTest",
                    kotlin =
                        listOf(
                            "build/generated/common/test/kotlin",
                            "build/generated/sources/buildConfig/test",
                            "common/test/kotlin",
                        ),
                    resources = listOf("common/test/data"),
                ),
                sourceSet(
                    name = "cliMain",
                    kotlin =
                        listOf(
                            "build/generated/cli/main/kotlin",
                            "build/generated/sources/buildConfig/cliMain",
                            "cli/main/kotlin",
                        ),
                    resources = listOf("build/generated/cli/main/resources", "cli/main/resources"),
                ),
                sourceSet(
                    name = "jsTest",
                    kotlin =
                        listOf(
                            "build/generated/js/test/kotlin",
                            "build/generated/sources/buildConfig/jsTest",
                            "js/test/kotlin",
                        ),
                    resources = listOf("js/test/data"),
                ),
                sourceSet(
                    name = "generateTests",
                    kotlin =
                        listOf(
                            "build/generated/common/generateTests/kotlin",
                            "build/generated/sources/buildConfig/generateTests",
                            "common/generateTests/kotlin",
                        ),
                    resources =
                        listOf(
                            "build/generated/common/generateTests/resources",
                            "common/generateTests/resources",
                        ),
                ),
                sourceSet(
                    name = "generateTestsCli",
                    kotlin =
                        listOf(
                            "build/generated/cli/generateTests/kotlin",
                            "build/generated/sources/buildConfig/generateTestsCli",
                            "cli/generateTests/kotlin",
                        ),
                    resources =
                        listOf(
                            "build/generated/cli/generateTests/resources",
                            "cli/generateTests/resources",
                        ),
                ),
                // The leaf `generateTests` source set gets the directory of its group
                sourceSet(
                    name = "k2421GenerateTests",
                    kotlin =
                        listOf(
                            "build/generated/sources/buildConfig/k2421GenerateTests",
                            "js/generateTests/kotlin",
                        ),
                    resources = listOf("js/generateTests/resources"),
                ),
                // The DevKit leaf source sets are untouched, so the generated tests are kept
                sourceSet(
                    name = "k2421Main",
                    kotlin = emptyList(),
                    resources = emptyList(),
                ),
                sourceSet(
                    name = "k2421Test",
                    kotlin = listOf("build/test-gen/k2421"),
                    resources = emptyList(),
                ),
                // `entryPoint` keeps the generated `META-INF/services` files
                sourceSet(
                    name = "entryPoint",
                    kotlin = listOf("build/generated/sources/buildConfig/entryPoint"),
                    resources = listOf("build/compiler-plugin-devkit/generated-resources"),
                ),
            )

            sourceSets.filter { it.contains("[src/") || it.contains(" src/") }.shouldBeEmpty()
        }
    }

    @Test
    fun `DevKit compiler library compiles the Hubdle main source set`() {
        gradleTestKitTest("$basePath/compiler-library") {
            gradlew("assemble", "--no-scan").output.shouldContain("BUILD SUCCESSFUL")
            projectDir
                .resolve("build")
                .walkTopDown()
                .filter { it.name == "NamesKt.class" }
                .toList()
                .shouldNotBeEmpty()
        }
    }
}

internal class KotlinCompilerLibraryIdeTest : GradleTestKitTest() {

    @Test
    fun `DevKit compiler library compiles against the IDE compilers by default`() {
        gradleTestKitTest("kotlin/compiler/devkit/compiler-library-ide") {
            val output: String = gradlew("assemble", "--no-scan").output
            output.shouldContain("BUILD SUCCESSFUL")

            // IntelliJ IDEA 2026.3 targets, for example `k250_ij263_18`
            val ideTargets: List<String> =
                output
                    .lines()
                    .map(String::trim)
                    .filter { it.startsWith("TARGET ") }
                    .map { it.removePrefix("TARGET ") }
                    .filter { IdeTargetRegex.matches(it) }
            ideTargets.shouldNotBeEmpty()
            for (target in ideTargets) {
                val task = "compileKotlin${target.replaceFirstChar(Char::uppercaseChar)}"
                output.shouldContain("Task :$task")
            }
        }
    }
}

private val IdeTargetRegex = Regex("""^k\d+_ij263_\d+$""")

private fun sourceSet(name: String, kotlin: List<String>, resources: List<String>): String =
    "SOURCE_SET $name kotlin=$kotlin resources=$resources"
