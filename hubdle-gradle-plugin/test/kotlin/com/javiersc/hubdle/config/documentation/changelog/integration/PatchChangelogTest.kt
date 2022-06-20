package com.javiersc.hubdle.config.documentation.changelog.integration

import com.javiersc.gradle.testkit.test.extensions.gradleTestKitTest
import com.javiersc.gradle.testkit.test.extensions.gradlewArgumentFromTXT
import com.javiersc.gradle.testkit.test.extensions.testConfigurationCache
import com.javiersc.gradle.testkit.test.extensions.withArgumentsFromTXT
import com.javiersc.hubdle.config.documentation.changelog.utils.testChangelog
import kotlin.test.Test
import org.gradle.testkit.runner.TaskOutcome

internal class PatchChangelogTest {

    private val basePath = "config/documentation/changelog/patch-changelog"

    @Test
    fun `patch 1`() {
        gradleTestKitTest("$basePath/sandbox-patch-1") {
            val result = gradlewArgumentFromTXT()
            testChangelog(result, projectDir)
        }
    }

    @Test
    fun `patch configuration cache 1`() {
        gradleTestKitTest("$basePath/sandbox-patch-configuration-cache-1", isolated = true) {
            withArgumentsFromTXT()
            val result = build()
            testChangelog(result, projectDir)
            testConfigurationCache(expectTaskOutcome = TaskOutcome.SUCCESS)
        }
    }
}
