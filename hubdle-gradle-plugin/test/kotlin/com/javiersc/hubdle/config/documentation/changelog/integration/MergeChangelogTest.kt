package com.javiersc.hubdle.config.documentation.changelog.integration

import com.javiersc.gradle.testkit.test.extensions.GradleTest
import com.javiersc.hubdle.config.documentation.changelog.utils.testChangelog
import kotlin.test.Test

internal class MergeChangelogTest : GradleTest() {

    private val basePath = "config/documentation/changelog/merge-changelog"

    @Test
    fun `merge 1`() {
        gradleTestKitTest("$basePath/sandbox-merge-1") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `merge 2`() {
        gradleTestKitTest("$basePath/sandbox-merge-2") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `merge 3`() {
        gradleTestKitTest("$basePath/sandbox-merge-3") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `merge 4`() {
        gradleTestKitTest("$basePath/sandbox-merge-4") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }
}
