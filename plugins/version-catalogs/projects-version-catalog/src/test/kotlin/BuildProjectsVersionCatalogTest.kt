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

    @Test
    fun `build projects version catalog 3`() =
        testSandbox(sandboxPath = "version-catalog-3", test = ::testProjectsVersionCatalog)

    @Test
    fun `build projects version catalog 4`() =
        testSandbox(sandboxPath = "version-catalog-4", test = ::testProjectsVersionCatalog)

    @Test
    fun `build projects version catalog 5`() =
        testSandbox(sandboxPath = "version-catalog-5", test = ::testProjectsVersionCatalog)
}

@Suppress("UNUSED_PARAMETER")
fun testProjectsVersionCatalog(result: BuildResult, testProjectDir: File) {
    val expect = File("$testProjectDir/projects.versions.toml")
    val actual = File("$testProjectDir/projects.versions-actual.toml")

    expect.shouldHaveSameContentAs(actual)
}
