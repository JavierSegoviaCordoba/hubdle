package com.javiersc.hubdle.kotlin.jvm

import com.javiersc.gradle.testkit.test.extensions.gradleTestKitTest
import com.javiersc.gradle.testkit.test.extensions.gradlewArgumentFromTXT
import com.javiersc.gradle.testkit.test.extensions.withArgumentsFromTXT
import io.kotest.matchers.file.shouldBeADirectory
import io.kotest.matchers.file.shouldBeAFile
import io.kotest.matchers.string.shouldContain
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

internal class KotlinJvmTest {

    private val basePath = "kotlin/jvm"

    private val repositoryPath = File(System.getProperty("user.home")).resolve(".m2/repository")

    @BeforeTest
    fun `clean m2_com_kotlin before test`() {
        repositoryPath.resolve("com/kotlin/jvm").deleteRecursively()
    }

    @AfterTest
    fun `clean m2_com_kotlin after test`() {
        repositoryPath.resolve("com/kotlin/jvm").deleteRecursively()
    }

    @Test
    fun `publish failed 1`() {
        gradleTestKitTest("$basePath/publishing/failed-1") {
            withArgumentsFromTXT()
            buildAndFail()
                .output.shouldContain(
                    "A problem was found with the configuration of task ':signJavaPublication'"
                )
        }
    }

    @Test
    fun `publish normal 1`() {
        val jvmDir = repositoryPath.resolve("com/kotlin/jvm")
        val sandboxProjectDir = jvmDir.resolve("sandbox-project")
        val versionDir = sandboxProjectDir.resolve("9.8.3-alpha.4")
        gradleTestKitTest("$basePath/publishing/normal-1") {
            gradlewArgumentFromTXT()
            sandboxProjectDir.shouldBeADirectory()
            sandboxProjectDir.resolve("maven-metadata-local.xml").shouldBeAFile()
            versionDir.shouldBeADirectory()
            versionDir.resolve("sandbox-project-9.8.3-alpha.4.jar").shouldBeAFile()
            versionDir.resolve("sandbox-project-9.8.3-alpha.4.module").shouldBeAFile()
            versionDir.resolve("sandbox-project-9.8.3-alpha.4.pom").shouldBeAFile()
            versionDir.resolve("sandbox-project-9.8.3-alpha.4-javadoc.jar").shouldBeAFile()
            versionDir.resolve("sandbox-project-9.8.3-alpha.4-sources.jar").shouldBeAFile()
        }
    }

    @Test
    fun `publish snapshot 1`() {
        val jvmDir = repositoryPath.resolve("com/kotlin/jvm")
        val sandboxProjectDir = jvmDir.resolve("sandbox-project")
        val versionDir = sandboxProjectDir.resolve("3.6.7-SNAPSHOT")
        gradleTestKitTest("$basePath/publishing/snapshot-1") {
            gradlewArgumentFromTXT()
            sandboxProjectDir.shouldBeADirectory()
            sandboxProjectDir.resolve("maven-metadata-local.xml").shouldBeAFile()
            versionDir.shouldBeADirectory()
            versionDir.resolve("sandbox-project-3.6.7-SNAPSHOT.jar").shouldBeAFile()
            versionDir.resolve("sandbox-project-3.6.7-SNAPSHOT.module").shouldBeAFile()
            versionDir.resolve("sandbox-project-3.6.7-SNAPSHOT.pom").shouldBeAFile()
            versionDir.resolve("sandbox-project-3.6.7-SNAPSHOT-javadoc.jar").shouldBeAFile()
            versionDir.resolve("sandbox-project-3.6.7-SNAPSHOT-sources.jar").shouldBeAFile()
        }
    }
}
