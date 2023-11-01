package com.javiersc.hubdle.project.kotlin.jvm

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.file.shouldBeADirectory
import io.kotest.matchers.file.shouldBeAFile
import io.kotest.matchers.file.shouldNotExist
import io.kotest.matchers.string.shouldContain
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import org.gradle.testkit.runner.GradleRunner

internal class KotlinJvmTest : GradleTestKitTest() {

    private val basePath = "kotlin/jvm"

    private val mavenLocalPath =
        File(System.getProperty("user.home"))
            .resolve(".m2/repository/com/kotlin/jvm/sandbox-project/")

    private val projectName = "sandbox-project"

    private val GradleRunner.mavenLocalRandomPath
        get() =
            projectDir.resolve("build/mavenLocalRandom/repository/com/kotlin/jvm/sandbox-project/")

    private val GradleRunner.mavenLocalBuildTestPath
        get() =
            projectDir.resolve(
                "build/mavenLocalBuildTest/repository/com/kotlin/jvm/sandbox-project/"
            )

    @BeforeTest
    fun `clean m2_com_kotlin before test`() {
        mavenLocalPath.deleteRecursively()
    }

    @AfterTest
    fun `clean m2_com_kotlin after test`() {
        mavenLocalPath.deleteRecursively()
    }

    @Test
    fun `publish maven-local-1`() {
        gradleTestKitTest("$basePath/publishing/maven-local-1", name = projectName) {
            withEnvironment(gpgMap)
            withArgumentsFromTXT()
            build().output.also(::println)
            mavenLocalPath
                .resolve("9.8.3-alpha.4")
                .listFiles()
                .orEmpty()
                .filter { it.isFile && it.extension == "asc" }
                .shouldHaveSize(0)
        }
    }

    @Test
    fun `publish maven-local-test-1`() {
        gradleTestKitTest("$basePath/publishing/maven-local-build-test-1", name = projectName) {
            withEnvironment(gpgMap)
            withArgumentsFromTXT()
            build().output.also(::println)
            mavenLocalBuildTestPath
                .resolve("9.8.3-alpha.4")
                .listFiles()
                .orEmpty()
                .filter { it.isFile && it.extension == "asc" }
                .shouldHaveSize(0)
        }
    }

    @Test
    fun `publish maven-local-random-1`() {
        gradleTestKitTest("$basePath/publishing/maven-local-random-1", name = projectName) {
            withEnvironment(gpgMap)
            withArgumentsFromTXT()
            build().output.also(::println)
            mavenLocalRandomPath
                .resolve("9.8.3-alpha.4")
                .listFiles()
                .orEmpty()
                .filter { it.isFile && it.extension == "asc" }
                .shouldHaveSize(5)
        }
    }

    @Test
    fun `publish conditional 1`() {
        gradleTestKitTest("$basePath/publishing/conditional-1", name = projectName) {
            gradlewArgumentFromTXT()
            val publicationDir = mavenLocalPath.parentFile
            publicationDir.shouldBeADirectory()
            val publishedProjectDir = publicationDir.resolve("one")
            publishedProjectDir.shouldBeADirectory()
            publicationDir.resolve("two").shouldNotExist()
            publishedProjectDir.resolve("maven-metadata-local.xml").shouldBeAFile()
            val versionDir = publishedProjectDir.resolve("9.8.3-alpha.4")
            versionDir.shouldBeADirectory()
            versionDir.resolve("one-9.8.3-alpha.4.jar").shouldBeAFile()
            versionDir.resolve("one-9.8.3-alpha.4.module").shouldBeAFile()
            versionDir.resolve("one-9.8.3-alpha.4.pom").shouldBeAFile()
            versionDir.resolve("one-9.8.3-alpha.4-javadoc.jar").shouldBeAFile()
            versionDir.resolve("one-9.8.3-alpha.4-sources.jar").shouldBeAFile()
        }
    }

    @Test
    fun `publish normal 1`() {
        val versionDir = mavenLocalPath.resolve("9.8.3-alpha.4")
        gradleTestKitTest("$basePath/publishing/normal-1", name = projectName) {
            gradlewArgumentFromTXT()
            mavenLocalPath.shouldBeADirectory()
            mavenLocalPath.resolve("maven-metadata-local.xml").shouldBeAFile()
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
        val versionDir = mavenLocalPath.resolve("3.6.7-SNAPSHOT")
        gradleTestKitTest("$basePath/publishing/snapshot-1", name = projectName) {
            gradlewArgumentFromTXT()
            mavenLocalPath.shouldBeADirectory()
            mavenLocalPath.resolve("maven-metadata-local.xml").shouldBeAFile()
            versionDir.shouldBeADirectory()
            versionDir.resolve("sandbox-project-3.6.7-SNAPSHOT.jar").shouldBeAFile()
            versionDir.resolve("sandbox-project-3.6.7-SNAPSHOT.module").shouldBeAFile()
            versionDir.resolve("sandbox-project-3.6.7-SNAPSHOT.pom").shouldBeAFile()
            versionDir.resolve("sandbox-project-3.6.7-SNAPSHOT-javadoc.jar").shouldBeAFile()
            versionDir.resolve("sandbox-project-3.6.7-SNAPSHOT-sources.jar").shouldBeAFile()
        }
    }

    @Test
    fun `publish gradle-plugin-1`() {
        gradleTestKitTest("$basePath/publishing/gradle-plugin-1", name = projectName) {
            withEnvironment(gpgMap)
            withArgumentsFromTXT()
            build().output.shouldContain("Publications: [pluginMaven, sandboxPluginMarkerMaven]")
        }
    }
}

private val gpgMap =
    mapOf(
        "SIGNING_GNUPG_KEY" to gpgKey,
        "SIGNING_GNUPG_PASSPHRASE" to gpgKeyPassphrase,
    )

private val gpgKey
    get() =
        """
            -----BEGIN PGP PRIVATE KEY BLOCK-----
        
            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
            =ZuCh
            -----END PGP PRIVATE KEY BLOCK-----
        """
            .trimIndent()

private val gpgKeyPassphrase
    get() = "12345678"
