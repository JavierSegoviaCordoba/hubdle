package com.javiersc.hubdle.config

import com.javiersc.gradle.testkit.test.extensions.GradleTest
import io.kotest.matchers.file.shouldBeADirectory
import io.kotest.matchers.file.shouldBeAFile
import io.kotest.matchers.string.shouldContain
import java.io.File
import kotlin.test.Test

internal class BinaryCompatibilityValidatorTest : GradleTest() {

    private val basePath = "config/binary-compatibility-validator"

    @Test
    fun `binary compatibility validator 1`() {
        gradleTestKitTest("$basePath/1") {
            gradlewArgumentFromTXT()
            val rootApiDir: File = projectDir.resolve("api")
            rootApiDir.shouldBeADirectory()
            val rootApiFile: File = rootApiDir.resolve("sandbox-project.api")
            rootApiFile.shouldBeAFile()
            rootApiFile
                .readText()
                .shouldContain(
                    """
                        |public final class com/kotlin/jvm/sandbox/project/Main {
                        |	public fun <init> ()V
                        |}
                    """.trimMargin()
                )

            val libraryApiDir: File = projectDir.resolve("library/api")
            libraryApiDir.shouldBeADirectory()
            val libraryApiFile: File = libraryApiDir.resolve("library.api")
            libraryApiFile.shouldBeAFile()
            libraryApiFile
                .readText()
                .shouldContain(
                    """
                        |public final class com/kotlin/jvm/sandbox/project/library/Main {
                        |	public fun <init> ()V
                        |}
                    """.trimMargin()
                )
        }
    }
}
