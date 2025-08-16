package com.javiersc.hubdle.project.gradle

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import io.kotest.matchers.file.shouldExist
import io.kotest.matchers.string.shouldContain
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

internal class GradleTest : GradleTestKitTest() {

    private val basePath = "gradle"

    private val mavenLocalPath =
        File(System.getProperty("user.home")).resolve(".m2/repository/random/gradle/test/sandbox/")

    private val projectName = "sandbox"

    @BeforeTest
    fun `clean m2_com_kotlin before test`() {
        mavenLocalPath.deleteRecursively()
    }

    @AfterTest
    fun `clean m2_com_kotlin after test`() {
        mavenLocalPath.deleteRecursively()
    }

    @Test
    fun `build and publish gradle plugin-1`() {
        gradleTestKitTest("$basePath/plugin", name = projectName) {
            withEnvironment(gpgMap)
            gradlew("build").output.shouldContain("BUILD SUCCESSFUL")
            gradlew("publishToMavenLocal")
                .output
                .shouldContain("BUILD SUCCESSFUL")
                .shouldContain("Publications: [pluginMaven, sandboxPluginMarkerMaven]")
        }
    }

    @Test
    fun `build and publish gradle version-catalog-1`() {
        gradleTestKitTest("$basePath/version-catalog", name = projectName) {
            val moduleDir: File = projectDir.resolve("sandbox")
            val disabledCatalog: File = moduleDir.resolve("sandbox.libs.versions.toml.disabled")
            val enabledCatalog: File = moduleDir.resolve("sandbox.libs.versions.toml")
            disabledCatalog.renameTo(enabledCatalog)
            withEnvironment(gpgMap)
            gradlew("build").output.shouldContain("BUILD SUCCESSFUL")
            gradlew("publishToMavenLocal")
                .output
                .shouldContain("BUILD SUCCESSFUL")
                .shouldContain("Publications: [maven]")

            val publishedFiles: List<File> =
                listOf(
                    mavenLocalPath.resolve("9.8.3-alpha.4"),
                    mavenLocalPath.resolve("maven-metadata-local.xml"),
                    mavenLocalPath.resolve("9.8.3-alpha.4/sandbox-9.8.3-alpha.4.module"),
                    mavenLocalPath.resolve("9.8.3-alpha.4/sandbox-9.8.3-alpha.4.pom"),
                    mavenLocalPath.resolve("9.8.3-alpha.4/sandbox-9.8.3-alpha.4.toml"),
                )
            publishedFiles.onEach(File::shouldExist)
        }
    }
}

private val gpgMap =
    mapOf("SIGNING_GNUPG_KEY" to gpgKey, "SIGNING_GNUPG_PASSPHRASE" to gpgKeyPassphrase)

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
