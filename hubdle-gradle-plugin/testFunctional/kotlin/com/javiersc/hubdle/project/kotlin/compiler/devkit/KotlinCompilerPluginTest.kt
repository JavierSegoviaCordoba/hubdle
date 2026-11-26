package com.javiersc.hubdle.project.kotlin.compiler.devkit

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.file.shouldBeAFile
import io.kotest.matchers.file.shouldNotExist
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain
import java.io.File
import java.util.zip.ZipFile
import org.junit.jupiter.api.Test

internal class KotlinCompilerPluginTest : GradleTestKitTest() {

    private val basePath = "kotlin/compiler/devkit"

    @Test
    fun `DevKit compiler plugin tests are generated and run with the Hubdle layout`() {
        gradleTestKitTest("$basePath/compiler-plugin") {
            val boxDir: File = projectDir.resolve("common/test/data/box")

            // The first run writes the missing dumps next to the test data, and fails
            withArguments("allTests", "--no-scan").buildAndFail().output.apply {
                shouldContain("Expected data file did not exist")
                shouldNotContain("Unresolved reference")
            }
            boxDir.resolve("simple.fir.txt").shouldBeAFile()
            boxDir.resolve("simple.ir.txt").shouldBeAFile()

            // The test generator in `common/generateTests` found the test data in
            // `common/test/data`
            projectDir
                .resolve("build/test-gen")
                .walkTopDown()
                .filter { it.name == "JvmBoxTestGenerated.java" }
                .map(File::readText)
                .filter { it.contains("""@TestMetadata("common/test/data/box")""") }
                .filter { it.contains("public void testSimple()") }
                .toList()
                .shouldNotBeEmpty()

            // The second run uses the dumps of the first one
            gradlew("allTests", "--no-scan").output.shouldContain("BUILD SUCCESSFUL")

            gradlew("deleteCompilerTextTestFiles", "--no-scan")
                .output
                .shouldContain("BUILD SUCCESSFUL")
            boxDir.resolve("simple.fir.txt").shouldNotExist()
            boxDir.resolve("simple.ir.txt").shouldNotExist()
            boxDir.resolve("simple.kt").shouldBeAFile()
        }
    }

    @Test
    fun `deleteCompilerTextTestFiles deletes the dumps of every DevKit test source set`() {
        gradleTestKitTest("$basePath/compiler-plugin") {
            // Fake dumps, created here so the repository has no dump files
            val commonDumps: File = projectDir.resolve("common/test/data/dumps")
            val jsDumps: File = projectDir.resolve("js/test/data/dumps")
            commonDumps.writeFiles(
                "foo.kt",
                "foo.fir.txt",
                "foo.2420.fir.txt",
                "foo.2420.fir.patch",
                "TODO.fir.txt",
                "disabled.fir.ir.txt",
            )
            jsDumps.writeFiles("bar.kt", "bar.fir.txt")

            gradlew("deleteCompilerTextTestFiles", "--no-scan")
                .output
                .shouldContain("BUILD SUCCESSFUL")

            projectDir
                .resolve("common/test/data/dumps")
                .fileNames()
                .shouldContainExactlyInAnyOrder("TODO.fir.txt", "disabled.fir.ir.txt", "foo.kt")
            projectDir
                .resolve("js/test/data/dumps")
                .fileNames()
                .shouldContainExactlyInAnyOrder("bar.kt")
        }
    }

    @Test
    fun `DevKit compiler plugin publishes a multi-release jar`() {
        gradleTestKitTest("$basePath/compiler-plugin") {
            val repository: File = projectDir.resolve("build/m2")
            gradlew("publishToMavenLocal", "-Dmaven.repo.local=$repository", "--no-scan")
                .output
                .shouldContain("BUILD SUCCESSFUL")

            val groupDir: File = repository.resolve("com/kotlin/devkit")

            // The Kotlin Multiplatform root publication
            val rootDir: File = groupDir.resolve("sandbox-project/1.2.3")
            rootDir.resolve("sandbox-project-1.2.3.module").shouldBeAFile()
            rootDir.resolve("sandbox-project-1.2.3.pom").shouldBeAFile()

            // The publication of the surrogate target, which has the classes of every version
            val targetDirs: List<File> =
                groupDir.listFiles().orEmpty().filter { it.name.startsWith("sandbox-project-k") }
            targetDirs.shouldHaveSize(1)
            val target: String = targetDirs.single().name
            val targetDir: File = targetDirs.single().resolve("1.2.3")
            targetDir.resolve("$target-1.2.3.module").shouldBeAFile()
            targetDir.resolve("$target-1.2.3-sources.jar").shouldBeAFile()

            val pom: String = targetDir.resolve("$target-1.2.3.pom").readText()
            pom.shouldContain("<name>Sandbox name</name>")
            pom.shouldNotContain("<dependencies>")

            ZipFile(targetDir.resolve("$target-1.2.3.jar")).use { jar ->
                val entries: List<String> = jar.entries().toList().map { it.name }
                val manifest: String =
                    jar.getInputStream(jar.getEntry("META-INF/MANIFEST.MF")).reader().readText()

                manifest.shouldContain("Kotlin-Compiler-Plugin-Multi-Release: true")
                entries.shouldContain(
                    "META-INF/services/org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar"
                )
                entries.shouldContain(
                    "META-INF/services/org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor"
                )
                entries
                    .filter { it.startsWith("META-INF/kotlin/plugin/com.kotlin.devkit/versions/") }
                    .filter { it.endsWith("/com/kotlin/devkit/DevKitPluginRegistrar.class") }
                    .shouldNotBeEmpty()
                entries.filter { it.startsWith("com/kotlin/devkit/DevKitPlugin") }.shouldBeEmpty()
            }
        }
    }
}

private fun File.writeFiles(vararg names: String) {
    mkdirs()
    for (name in names) resolve(name).writeText("DUMP")
}

private fun File.fileNames(): List<String> =
    walkTopDown().filter(File::isFile).map(File::getName).toList()
