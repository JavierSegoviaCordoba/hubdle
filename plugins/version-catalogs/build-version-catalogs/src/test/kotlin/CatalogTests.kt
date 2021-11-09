@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.plugins.build.version.catalogs

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class CatalogTests {

    @Test
    fun `all tests`() {
        CatalogTestData.values().forEach { testData ->
            println(testData.name)
            val expect = Catalog(testData.rawData).data
            val actual = testData.expect

            expect shouldBe actual
        }
    }
}
