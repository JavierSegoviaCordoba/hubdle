package com.javiersc.hubdle.config.documentation.changelog.integration

import com.javiersc.gradle.testkit.extensions.gradleTestKitTest
import com.javiersc.gradle.testkit.extensions.gradlewArgumentFromTXT
import com.javiersc.gradle.testkit.extensions.testConfigurationCache
import com.javiersc.gradle.testkit.extensions.withArgumentsFromTXT
import com.javiersc.hubdle.config.documentation.changelog.utils.testChangelog
import kotlin.test.Test
import org.gradle.testkit.runner.TaskOutcome

class PatchChangelogTest {

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
        gradleTestKitTest("$basePath/sandbox-patch-configuration-cache-1") {
            withArgumentsFromTXT()
            val result = build()
            testChangelog(result, projectDir)
            testConfigurationCache(expectTaskOutcome = TaskOutcome.SUCCESS)
        }
    }
}
