package com.javiersc.hubdle.project.config.documentation.changelog

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import kotlin.test.Test

internal class MergeChangelogTest : GradleTestKitTest() {

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

    @Test
    fun `merge 5`() {
        gradleTestKitTest("$basePath/sandbox-merge-5") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `merge 6`() {
        gradleTestKitTest("$basePath/sandbox-merge-6") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }
}
