package com.javiersc.hubdle.config.documentation.changelog.integration

import com.javiersc.gradle.testkit.test.extensions.GradleTest
import com.javiersc.hubdle.commitAndCheckout
import com.javiersc.hubdle.config.documentation.changelog.utils.testChangelog
import kotlin.test.Test
import org.gradle.testkit.runner.TaskOutcome

internal class AddChangelogItemTest : GradleTest() {

    private val basePath = "config/documentation/changelog/add-changelog-item"

    @Test
    fun `added 1`() {
        gradleTestKitTest("$basePath/sandbox-added-1") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `added configuration cache 1`() {
        gradleTestKitTest("$basePath/sandbox-added-configuration-cache-1") {
            withArgumentsFromTXT()
            build()
            projectDir.testChangelog()
            testConfigurationCache(expectTaskOutcome = TaskOutcome.SUCCESS)
        }
    }

    @Test
    fun `changed 1`() {
        gradleTestKitTest("$basePath/sandbox-changed-1") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `deprecated 1`() {
        gradleTestKitTest("$basePath/sandbox-deprecated-1") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `removed 1`() {
        gradleTestKitTest("$basePath/sandbox-removed-1") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `fixed 1`() {
        gradleTestKitTest("$basePath/sandbox-fixed-1") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `updated 1`() {
        gradleTestKitTest("$basePath/sandbox-updated-1") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `renovate 1`() {
        gradleTestKitTest("$basePath/sandbox-renovate-1") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `renovate configuration cache 1`() {
        gradleTestKitTest("$basePath/sandbox-renovate-configuration-cache-1") {
            withArgumentsFromTXT()
            build()
            projectDir.testChangelog()
            testConfigurationCache(expectTaskOutcome = TaskOutcome.SUCCESS)
        }
    }

    @Test
    fun `renovate 2`() {
        gradleTestKitTest("$basePath/sandbox-renovate-2") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `renovate 3`() {
        gradleTestKitTest("$basePath/sandbox-renovate-3") {
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `renovate 4`() {
        gradleTestKitTest("$basePath/sandbox-renovate-4") {
            projectDir.commitAndCheckout(
                message =
                    """
                    | datasource | package                                                   | from  | to  |
                    | ---------- | --------------------------------------------------------- | ----- | --- |
                    | maven      | com.gradle.enterprise:com.gradle.enterprise.gradle.plugin | 3.6.4 | 3.7 |
                """.trimIndent(),
            )
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `renovate 5`() {
        gradleTestKitTest("$basePath/sandbox-renovate-5") {
            projectDir.commitAndCheckout(
                message =
                    """
                    | datasource | package                                                   | from  | to    |
                    | ---------- | --------------------------------------------------------- | ----- | ----- |
                    | maven      | com.gradle.enterprise:com.gradle.enterprise.gradle.plugin | 3.6.4 | 3.7   |
                    | maven      | com.javiersc.mokoki:mokoki-core                           | 1.0.0 | 1.0.1 |
                    | maven      | com.javiersc.either:either-core                           | 2.0.1 | 2.0.2 |
                    | maven      | com.javiersc.either:either-core                           | 2.0.1 | 2.0.2 |
                """.trimIndent(),
            )
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `renovate 6`() {
        gradleTestKitTest("$basePath/sandbox-renovate-6") {
            projectDir.commitAndCheckout(
                message =
                    """
                    | datasource | package                                                   | from  | to    |
                    | ---------- | --------------------------------------------------------- | ----- | ----- |
                    | maven      | com.javiersc.mokoki:mokoki-core                           | 1.0.0 | 1.0.1 |
                """.trimIndent(),
            )
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `renovate 7`() {
        gradleTestKitTest("$basePath/sandbox-renovate-7") {
            projectDir.commitAndCheckout(
                message =
                    """
                        | datasource  | package       | from   | to     |
                        | ----------- | ------------- | ------ | ------ |
                        | github-tags | actions/cache | v2.1.6 | v2.1.7 |
                        | github-tags | actions/cache | v2.1.6 | v2.1.7 |
                        | github-tags | actions/cache | v2.1.6 | v2.1.7 |
                        | github-tags | actions/cache | v2.1.6 | v2.1.7 |
                        | github-tags | actions/cache | v2.1.6 | v2.1.7 |
                        | github-tags | actions/cache | v2.1.6 | v2.1.7 |
                    """.trimIndent(),
            )
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }

    @Test
    fun `renovate 8`() {
        gradleTestKitTest("$basePath/sandbox-renovate-8") {
            projectDir.commitAndCheckout(
                message =
                    """
                        | datasource     | package | from  | to  |
                        | -------------- | ------- | ----- | --- |
                        | gradle-version | gradle  | 7.3.3 | 7.4 |
                    """.trimIndent(),
            )
            gradlewArgumentFromTXT()
            projectDir.testChangelog()
        }
    }
}
