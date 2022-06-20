package com.javiersc.hubdle.config.documentation.changelog.integration

import com.javiersc.gradle.testkit.test.extensions.gradleTestKitTest
import com.javiersc.gradle.testkit.test.extensions.gradlewArgumentFromTXT
import com.javiersc.hubdle.config.documentation.changelog.utils.testChangelog
import kotlin.test.Test

internal class MergeChangelogTest {

    private val basePath = "config/documentation/changelog/merge-changelog"

    @Test
    fun `merge 1`() {
        gradleTestKitTest("$basePath/sandbox-merge-1") {
            val result = gradlewArgumentFromTXT()
            testChangelog(result, projectDir)
        }
    }

    @Test
    fun `merge 2`() {
        gradleTestKitTest("$basePath/sandbox-merge-2") {
            val result = gradlewArgumentFromTXT()
            testChangelog(result, projectDir)
        }
    }

    @Test
    fun `merge 3`() {
        gradleTestKitTest("$basePath/sandbox-merge-3") {
            val result = gradlewArgumentFromTXT()
            testChangelog(result, projectDir)
        }
    }

    @Test
    fun `merge 4`() {
        gradleTestKitTest("$basePath/sandbox-merge-4") {
            val result = gradlewArgumentFromTXT()
            testChangelog(result, projectDir)
        }
    }
}
