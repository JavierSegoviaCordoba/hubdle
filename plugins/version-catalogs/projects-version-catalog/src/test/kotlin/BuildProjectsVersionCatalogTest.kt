package com.javiersc.gradle.plugins.docs

import com.javiersc.gradle.plugins.core.test.testSandbox
import io.kotest.matchers.file.shouldHaveSameContentAs
import java.io.File
import org.gradle.testkit.runner.BuildResult
import org.junit.Test

class BuildProjectsVersionCatalogTest {

    @Test
    fun `build projects version catalog 1`() =
        testSandbox(sandboxPath = "version-catalog-1", test = ::testProjectsVersionCatalog)

    @Test
    fun `build projects version catalog 2`() =
        testSandbox(sandboxPath = "version-catalog-2", test = ::testProjectsVersionCatalog)
}

@Suppress("UNUSED_PARAMETER")
fun testProjectsVersionCatalog(result: BuildResult, testProjectDir: File) {
    val expect = File("$testProjectDir/gradle/projects.versions.toml")
    val actual = File("$testProjectDir/gradle/projects.versions-actual.toml")

    expect.shouldHaveSameContentAs(actual)
}
