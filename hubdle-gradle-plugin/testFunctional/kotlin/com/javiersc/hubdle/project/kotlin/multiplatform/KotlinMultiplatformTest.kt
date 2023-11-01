package com.javiersc.hubdle.project.kotlin.multiplatform

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import com.javiersc.hubdle.project.fixtures.CiEnv
import com.javiersc.hubdle.project.fixtures.FalseOrNullPattern
import com.javiersc.hubdle.project.fixtures.KotlinVersionEnv
import com.javiersc.hubdle.project.fixtures.KotlinVersionRegexOrEmptyOrNull
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.file.shouldBeADirectory
import io.kotest.matchers.file.shouldBeAFile
import io.kotest.matchers.shouldBe
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.junit.jupiter.api.condition.EnabledIfSystemProperty
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.OS

internal class KotlinMultiplatformTest : GradleTestKitTest() {

    private val basePath = "kotlin/multiplatform"

    private val repositoryPath = File(System.getProperty("user.home")).resolve(".m2/repository")

    private val projectName = "sandbox-project"

    @BeforeTest
    fun `clean m2_com_kotlin_multiplatform before test`() {
        repositoryPath.resolve("com/kotlin").deleteRecursively()
    }

    @AfterTest
    fun `clean m2_com_kotlin_multiplatform after test`() {
        repositoryPath.resolve("com/kotlin").deleteRecursively()
    }

    @Test
    @EnabledOnOs(value = [OS.LINUX, OS.MAC])
    fun `publish normal 1`() {
        val multiplatformDir = repositoryPath.resolve("com/kotlin/normal-one")
        val sandboxProjectDir = multiplatformDir.resolve(projectName)
        val androidDir = multiplatformDir.resolve("sandbox-project-android")
        val androidDebugDir = multiplatformDir.resolve("sandbox-project-android-debug")
        val jvmDir = multiplatformDir.resolve("sandbox-project-jvm")

        fun File.versionDir(): File = resolve("9.8.3-alpha.4")

        gradleTestKitTest("$basePath/publishing/normal-1", name = projectName) {
            gradlewArgumentFromTXT()
            sandboxProjectDir.shouldBeADirectory()
            sandboxProjectDir.resolve("maven-metadata-local.xml").shouldBeAFile()

            fun library(name: String? = null) =
                if (name == null) "sandbox-project-9.8.3-alpha.4"
                else "sandbox-project-$name-9.8.3-alpha.4"

            sandboxProjectDir.versionDir().shouldBeADirectory()
            sandboxProjectDir.versionDir().resolve("${library()}.jar").shouldBeAFile()
            sandboxProjectDir.versionDir().resolve("${library()}.module").shouldBeAFile()
            sandboxProjectDir.versionDir().resolve("${library()}.pom").shouldBeAFile()
            sandboxProjectDir
                .versionDir()
                .resolve("${library()}-kotlin-tooling-metadata.json")
                .shouldBeAFile()
            sandboxProjectDir.versionDir().resolve("${library()}-sources.jar").shouldBeAFile()

            androidDir.resolve("maven-metadata-local.xml").shouldBeAFile()
            androidDir.versionDir().shouldBeADirectory()
            androidDir.versionDir().resolve("${library("android")}.aar").shouldBeAFile()
            androidDir.versionDir().resolve("${library("android")}.module").shouldBeAFile()
            androidDir.versionDir().resolve("${library("android")}.pom").shouldBeAFile()
            androidDir.versionDir().resolve("${library("android")}-sources.jar").shouldBeAFile()

            androidDebugDir.resolve("maven-metadata-local.xml").shouldBeAFile()
            androidDebugDir.versionDir().shouldBeADirectory()
            androidDebugDir.versionDir().resolve("${library("android-debug")}.aar").shouldBeAFile()
            androidDebugDir
                .versionDir()
                .resolve("${library("android-debug")}.module")
                .shouldBeAFile()
            androidDebugDir.versionDir().resolve("${library("android-debug")}.pom").shouldBeAFile()
            androidDebugDir
                .versionDir()
                .resolve("${library("android-debug")}-sources.jar")
                .shouldBeAFile()

            jvmDir.resolve("maven-metadata-local.xml").shouldBeAFile()
            jvmDir.versionDir().shouldBeADirectory()
            jvmDir.versionDir().resolve("${library("jvm")}.jar").shouldBeAFile()
            jvmDir.versionDir().resolve("${library("jvm")}.module").shouldBeAFile()
            jvmDir.versionDir().resolve("${library("jvm")}.pom").shouldBeAFile()
            jvmDir.versionDir().resolve("${library("jvm")}-sources.jar").shouldBeAFile()
        }
    }

    @Test
    @EnabledOnOs(value = [OS.LINUX])
    @EnabledIfSystemProperty(named = KotlinVersionEnv, matches = KotlinVersionRegexOrEmptyOrNull)
    fun `publish normal 2 on LINUX`() {
        val multiplatformDir = repositoryPath.resolve("com/kotlin/normal-two")
        gradleTestKitTest("$basePath/publishing/normal-2", name = projectName) {
            gradlewArgumentFromTXT()
            val multiplatformDirs: List<File> =
                multiplatformDir.listFiles().shouldNotBeEmpty().sorted()

            multiplatformDirs.map { it.name }.shouldBe(allLinuxCompatibleBinaries)
            multiplatformDirs.forEach { multiplatformChild ->
                val children = multiplatformChild.listFiles().orEmpty()
                children.shouldHaveSize(2)
                val dir = children.first { it.extension != "xml" }
                val xml = children.first { it.extension == "xml" }
                dir.shouldBeADirectory()
                xml.shouldBeAFile()
                xml.name.shouldBe("maven-metadata-local.xml")
            }
        }
    }

    @Test
    @EnabledOnOs(value = [OS.MAC])
    @EnabledIfSystemProperty(named = KotlinVersionEnv, matches = KotlinVersionRegexOrEmptyOrNull)
    fun `publish normal 2 on MAC`() {
        val multiplatformDir = repositoryPath.resolve("com/kotlin/normal-two")
        gradleTestKitTest("$basePath/publishing/normal-2", name = projectName) {
            gradlewArgumentFromTXT()
            val multiplatformDirs: List<File> =
                multiplatformDir.listFiles().shouldNotBeEmpty().sorted()

            multiplatformDirs.map { it.name }.shouldBe(allMacCompatibleBinaries)
            multiplatformDirs.forEach { multiplatformChild ->
                val (dir, xml) =
                    multiplatformChild.listFiles().shouldNotBeEmpty().shouldHaveSize(2).toList()
                dir.shouldBeADirectory()
                xml.shouldBeAFile()
                xml.name.shouldBe("maven-metadata-local.xml")
            }
        }
    }

    @Test
    @EnabledOnOs(value = [OS.WINDOWS])
    @EnabledIfEnvironmentVariable(named = CiEnv, matches = FalseOrNullPattern)
    @EnabledIfSystemProperty(named = KotlinVersionEnv, matches = KotlinVersionRegexOrEmptyOrNull)
    fun `publish normal 2 on WINDOWS`() {
        val multiplatformDir = repositoryPath.resolve("com/kotlin/normal-two")
        gradleTestKitTest("$basePath/publishing/normal-2", name = projectName) {
            gradlew("kotlinUpgradeYarnLock")
            gradlewArgumentFromTXT()
            val multiplatformDirs: List<File> =
                multiplatformDir.listFiles().shouldNotBeEmpty().sorted()

            multiplatformDirs.map { it.name }.shouldBe(allWindowsCompatibleBinaries)
            multiplatformDirs.forEach { multiplatformChild ->
                val (dir, xml) =
                    multiplatformChild.listFiles().shouldNotBeEmpty().shouldHaveSize(2).toList()
                dir.shouldBeADirectory()
                xml.shouldBeAFile()
                xml.name.shouldBe("maven-metadata-local.xml")
            }
        }
    }

    @Test
    @EnabledOnOs(value = [OS.LINUX, OS.MAC])
    fun `publish snapshot 1`() {
        val multiplatformDir = repositoryPath.resolve("com/kotlin/snapshot-one")
        val sandboxProjectDir = multiplatformDir.resolve("sandbox-project")
        val androidDir = multiplatformDir.resolve("sandbox-project-android")
        val androidDebugDir = multiplatformDir.resolve("sandbox-project-android-debug")
        val jvmDir = multiplatformDir.resolve("sandbox-project-jvm")

        fun File.versionDir(): File = resolve("3.6.7-SNAPSHOT")

        gradleTestKitTest("$basePath/publishing/snapshot-1", name = projectName) {
            gradlewArgumentFromTXT()
            sandboxProjectDir.shouldBeADirectory()
            sandboxProjectDir.resolve("maven-metadata-local.xml").shouldBeAFile()

            fun library(name: String? = null) =
                if (name == null) "sandbox-project-3.6.7-SNAPSHOT"
                else "sandbox-project-$name-3.6.7-SNAPSHOT"

            sandboxProjectDir.versionDir().shouldBeADirectory()
            sandboxProjectDir.versionDir().resolve("maven-metadata-local.xml").shouldBeAFile()
            sandboxProjectDir.versionDir().resolve("${library()}.jar").shouldBeAFile()
            sandboxProjectDir.versionDir().resolve("${library()}.module").shouldBeAFile()
            sandboxProjectDir.versionDir().resolve("${library()}.pom").shouldBeAFile()
            sandboxProjectDir
                .versionDir()
                .resolve("${library()}-kotlin-tooling-metadata.json")
                .shouldBeAFile()
            sandboxProjectDir.versionDir().resolve("${library()}-sources.jar").shouldBeAFile()

            androidDir.resolve("maven-metadata-local.xml").shouldBeAFile()
            androidDir.versionDir().shouldBeADirectory()
            androidDir.versionDir().resolve("maven-metadata-local.xml").shouldBeAFile()
            androidDir.versionDir().resolve("${library("android")}.aar").shouldBeAFile()
            androidDir.versionDir().resolve("${library("android")}.module").shouldBeAFile()
            androidDir.versionDir().resolve("${library("android")}.pom").shouldBeAFile()
            androidDir.versionDir().resolve("${library("android")}-sources.jar").shouldBeAFile()

            androidDebugDir.resolve("maven-metadata-local.xml").shouldBeAFile()
            androidDebugDir.versionDir().shouldBeADirectory()
            androidDebugDir.versionDir().resolve("maven-metadata-local.xml").shouldBeAFile()
            androidDebugDir.versionDir().resolve("${library("android-debug")}.aar").shouldBeAFile()
            androidDebugDir
                .versionDir()
                .resolve("${library("android-debug")}.module")
                .shouldBeAFile()
            androidDebugDir.versionDir().resolve("${library("android-debug")}.pom").shouldBeAFile()
            androidDebugDir
                .versionDir()
                .resolve("${library("android-debug")}-sources.jar")
                .shouldBeAFile()

            jvmDir.resolve("maven-metadata-local.xml").shouldBeAFile()
            jvmDir.versionDir().shouldBeADirectory()
            jvmDir.versionDir().resolve("maven-metadata-local.xml").shouldBeAFile()
            jvmDir.versionDir().resolve("${library("jvm")}.jar").shouldBeAFile()
            jvmDir.versionDir().resolve("${library("jvm")}.module").shouldBeAFile()
            jvmDir.versionDir().resolve("${library("jvm")}.pom").shouldBeAFile()
            jvmDir.versionDir().resolve("${library("jvm")}-sources.jar").shouldBeAFile()
        }
    }

    private val allLinuxCompatibleBinaries =
        setOf(
                "sandbox-project-android",
                "sandbox-project-android-debug",
                "sandbox-project-androidnativearm32",
                "sandbox-project-androidnativearm64",
                "sandbox-project-androidnativex64",
                "sandbox-project-androidnativex86",
                "sandbox-project-js",
                "sandbox-project-jvm",
                "sandbox-project-linuxarm64",
                "sandbox-project-linuxx64",
                // "sandbox-project-mingwx64",
                "sandbox-project-wasm-js",
                "sandbox-project",
            )
            .sorted()

    private val allMacCompatibleBinaries =
        setOf(
                "sandbox-project-android",
                "sandbox-project-android-debug",
                "sandbox-project-androidnativearm32",
                "sandbox-project-androidnativearm64",
                "sandbox-project-androidnativex64",
                "sandbox-project-androidnativex86",
                "sandbox-project-iosarm64",
                "sandbox-project-iossimulatorarm64",
                "sandbox-project-iosx64",
                "sandbox-project-js",
                "sandbox-project-jvm",
                // "sandbox-project-linuxarm64",
                // "sandbox-project-linuxx64",
                "sandbox-project-macosarm64",
                "sandbox-project-macosx64",
                // "sandbox-project-mingwx64",
                "sandbox-project-tvosarm64",
                "sandbox-project-tvossimulatorarm64",
                "sandbox-project-tvosx64",
                "sandbox-project-wasm-js",
                "sandbox-project-watchosarm32",
                "sandbox-project-watchosarm64",
                "sandbox-project-watchosdevicearm64",
                "sandbox-project-watchossimulatorarm64",
                "sandbox-project-watchosx64",
                "sandbox-project",
            )
            .sorted()

    private val allWindowsCompatibleBinaries =
        setOf(
                "sandbox-project-android",
                "sandbox-project-android-debug",
                "sandbox-project-androidnativearm32",
                "sandbox-project-androidnativearm64",
                "sandbox-project-androidnativex64",
                "sandbox-project-androidnativex86",
                "sandbox-project-js",
                "sandbox-project-jvm",
                // "sandbox-project-linuxarm64",
                // "sandbox-project-linuxx64",
                "sandbox-project-mingwx64",
                "sandbox-project-wasm-js",
                "sandbox-project",
            )
            .sorted()
}
