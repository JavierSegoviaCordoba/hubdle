package com.javiersc.hubdle.config.documentation.changelog.integration

import com.javiersc.gradle.testkit.test.extensions.GradleTest
import com.javiersc.hubdle.config.documentation.changelog.utils.testChangelog
import kotlin.test.Test
import org.gradle.testkit.runner.TaskOutcome

internal class PatchChangelogTest : GradleTest() {

    private val basePath = "config/documentation/changelog/patch-changelog"

    @Test
    fun `patch 1`() {
        gradleTestKitTest("$basePath/sandbox-patch-1") {
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
