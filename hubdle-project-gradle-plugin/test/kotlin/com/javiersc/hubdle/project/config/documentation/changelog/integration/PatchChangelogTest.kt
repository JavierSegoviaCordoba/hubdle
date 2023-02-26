package com.javiersc.hubdle.project.config.documentation.changelog.integration

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import com.javiersc.hubdle.project.config.documentation.changelog.utils.testChangelog
import kotlin.test.Test
import org.gradle.testkit.runner.TaskOutcome

internal class PatchChangelogTest : GradleTestKitTest() {

    private val basePath = "config/documentation/changelog/patch-changelog"

    @Test
    fun `patch 1`() {
        gradleTestKitTest("$basePath/sandbox-patch-1") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `patch 2`() {
        gradleTestKitTest("$basePath/sandbox-patch-2") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `patch 3`() {
        gradleTestKitTest("$basePath/sandbox-patch-3") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `patch 4`() {
        gradleTestKitTest("$basePath/sandbox-patch-4") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `patch 5`() {
        gradleTestKitTest("$basePath/sandbox-patch-5") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `patch configuration cache 1`() {
        gradleTestKitTest("$basePath/sandbox-patch-configuration-cache-1") {
            withArgumentsFromTXT()
            build()
            projectDir.testChangelog()
            testConfigurationCache(expectTaskOutcome = TaskOutcome.SUCCESS)
        }
    }
}
