package hubdle.ecosystem.feature.documentation.changelog

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import hubdle.platform.HubdleProperties
import hubdle.platform.LogFixture.lifecycle
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContainInOrder
import io.kotest.matchers.string.shouldNotBeBlank
import io.kotest.matchers.string.shouldNotContain
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.test.Test
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Config
import org.eclipse.jgit.lib.GpgConfig
import org.gradle.testkit.runner.TaskOutcome

class HubdleEcosystemFeatureDocumentationChangelogTest : GradleTestKitTest() {

    @Test
    fun `GIVEN a project WHEN hubdle documentation changelog is enabled THEN changelog feature is applied`() {
        gradleTestKitTest("hubdle-feature-documentation-changelog/basic") {
            gradlew("tasks", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldContainInOrder(
                    lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" },
                    lifecycle("documentation") { "Feature 'documentation' enabled on ':'" },
                    lifecycle("changelog") { "Feature 'changelog' enabled on ':'" },
                )
        }
    }

    @Test
    fun `GIVEN a project WHEN hubdle disabled THEN changelog feature is not applied`() {
        gradleTestKitTest("hubdle-feature-documentation-changelog/hubdle-disabled") {
            gradlew("tasks", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldNotContain(lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" })
                .shouldNotContain(
                    lifecycle("documentation") { "Feature 'documentation' enabled on ':'" }
                )
                .shouldNotContain(lifecycle("changelog") { "Feature 'changelog' enabled on ':'" })
        }
    }

    @Test
    fun `GIVEN original add changelog item fixtures WHEN running DCL changelog THEN changelog matches expected`() {
        val sandboxes =
            listOf(
                "sandbox-added-1",
                "sandbox-changed-1",
                "sandbox-deprecated-1",
                "sandbox-fixed-1",
                "sandbox-removed-1",
                "sandbox-updated-1",
            )

        for (sandbox in sandboxes) {
            gradleTestKitTest("hubdle-feature-documentation-changelog/$sandbox") {
                gradlewArgumentFromTXT()
                projectDir.testChangelog()
            }
        }
    }

    @Test
    fun `GIVEN original add changelog item configuration cache fixture WHEN running DCL changelog THEN changelog matches expected`() {
        gradleTestKitTest(
            "hubdle-feature-documentation-changelog/sandbox-added-configuration-cache-1"
        ) {
            withArgumentsFromTXT()
            build()
            projectDir.testChangelog()
            testConfigurationCache(expectTaskOutcome = TaskOutcome.UP_TO_DATE)
        }
    }

    @Test
    fun `GIVEN original renovate PR fixtures WHEN running DCL changelog THEN changelog matches expected`() {
        val sandboxes =
            listOf(
                "sandbox-renovate-1",
                "sandbox-renovate-2",
                "sandbox-renovate-3",
                "sandbox-renovate-configuration-cache-1",
            )

        for (sandbox in sandboxes) {
            gradleTestKitTest("hubdle-feature-documentation-changelog/$sandbox") {
                if (sandbox == "sandbox-renovate-configuration-cache-1") {
                    withArgumentsFromTXT()
                    build()
                    projectDir.testChangelog()
                    testConfigurationCache(expectTaskOutcome = TaskOutcome.UP_TO_DATE)
                } else {
                    gradlewArgumentFromTXT()
                    projectDir.testChangelog()
                }
            }
        }
    }

    @Test
    fun `GIVEN original renovate commit fixtures WHEN running DCL changelog THEN changelog matches expected`() {
        val sandboxesWithMessages =
            mapOf(
                "sandbox-renovate-4" to
                    """
                    | datasource | package                                                   | from  | to  |
                    | ---------- | --------------------------------------------------------- | ----- | --- |
                    | maven      | com.gradle.enterprise:com.gradle.enterprise.gradle.plugin | 3.6.4 | 3.7 |
                    """
                        .trimIndent(),
                "sandbox-renovate-5" to
                    """
                    | datasource | package                                                   | from  | to    |
                    | ---------- | --------------------------------------------------------- | ----- | ----- |
                    | maven      | com.gradle.enterprise:com.gradle.enterprise.gradle.plugin | 3.6.4 | 3.7   |
                    | maven      | com.javiersc.mokoki:mokoki-core                           | 1.0.0 | 1.0.1 |
                    | maven      | com.javiersc.either:either-core                           | 2.0.1 | 2.0.2 |
                    | maven      | com.javiersc.either:either-core                           | 2.0.1 | 2.0.2 |
                    """
                        .trimIndent(),
                "sandbox-renovate-6" to
                    """
                    | datasource | package                                                   | from  | to    |
                    | ---------- | --------------------------------------------------------- | ----- | ----- |
                    | maven      | com.javiersc.mokoki:mokoki-core                           | 1.0.0 | 1.0.1 |
                    """
                        .trimIndent(),
                "sandbox-renovate-7" to
                    """
                    | datasource  | package       | from   | to     |
                    | ----------- | ------------- | ------ | ------ |
                    | github-tags | actions/cache | v2.1.6 | v2.1.7 |
                    | github-tags | actions/cache | v2.1.6 | v2.1.7 |
                    | github-tags | actions/cache | v2.1.6 | v2.1.7 |
                    | github-tags | actions/cache | v2.1.6 | v2.1.7 |
                    | github-tags | actions/cache | v2.1.6 | v2.1.7 |
                    | github-tags | actions/cache | v2.1.6 | v2.1.7 |
                    """
                        .trimIndent(),
                "sandbox-renovate-8" to
                    """
                    | datasource     | package | from  | to  |
                    | -------------- | ------- | ----- | --- |
                    | gradle-version | gradle  | 7.3.3 | 7.4 |
                    """
                        .trimIndent(),
                "sandbox-renovate-9" to
                    """

                    | datasource | package                                               | from                           | to                             |
                    | ---------- | ----------------------------------------------------- | ------------------------------ | ------------------------------ |
                    | maven      | com.javiersc.hubdle:com.javiersc.hubdle.gradle.plugin | 0.6.4+2.0.0-dev-17175-SNAPSHOT | 0.6.4+2.0.0-dev-18803-SNAPSHOT |
                    """
                        .trimIndent(),
            )

        for ((sandbox, message) in sandboxesWithMessages) {
            gradleTestKitTest("hubdle-feature-documentation-changelog/$sandbox") {
                projectDir.commitAndCheckout(message = message)
                gradlewArgumentFromTXT()
                projectDir.testChangelog()
            }
        }
    }

    @Test
    fun `GIVEN original merge changelog fixtures WHEN running DCL changelog THEN changelog matches expected`() {
        val sandboxes =
            listOf(
                "sandbox-merge-1",
                "sandbox-merge-2",
                "sandbox-merge-3",
                "sandbox-merge-4",
                "sandbox-merge-5",
                "sandbox-merge-6",
            )

        for (sandbox in sandboxes) {
            gradleTestKitTest("hubdle-feature-documentation-changelog/$sandbox") {
                gradlewArgumentFromTXT()
                projectDir.testChangelog()
            }
        }
    }

    @Test
    fun `GIVEN original patch changelog fixtures WHEN running DCL changelog THEN changelog matches expected`() {
        val sandboxes =
            listOf(
                "sandbox-patch-1",
                "sandbox-patch-2",
                "sandbox-patch-3",
                "sandbox-patch-4",
                "sandbox-patch-5",
            )

        for (sandbox in sandboxes) {
            gradleTestKitTest("hubdle-feature-documentation-changelog/$sandbox") {
                gradlewArgumentFromTXT()
                projectDir.testChangelog()
            }
        }
    }

    @Test
    fun `GIVEN original patch changelog configuration cache fixture WHEN running DCL changelog THEN changelog matches expected`() {
        gradleTestKitTest(
            "hubdle-feature-documentation-changelog/sandbox-patch-configuration-cache-1"
        ) {
            withArgumentsFromTXT()
            build()
            projectDir.testChangelog()
            testConfigurationCache(expectTaskOutcome = TaskOutcome.SUCCESS)
        }
    }
}

private fun File.commitAndCheckout(message: String, branch: String = "sandbox/hubdle") {
    val git: Git = Git.init().setDirectory(this).call()
    git.add().addFilepattern(".").call()
    git.commit().setSign(false).setGpgConfig(GpgConfig(Config())).setMessage(message).call()
    git.checkout().setCreateBranch(true).setName(branch).call()
}

private fun File.testChangelog() {
    updateChangelogExpectedDate()
    changelog.readText().shouldNotBeBlank()
    changelogExpected.readText().shouldNotBeBlank()
    changelog.readText().shouldBe(changelogExpected.readText())
}

private val File.changelog: File
    get() = resolve("CHANGELOG.md")

private val File.changelogExpected: File
    get() = resolve("CHANGELOG_EXPECT.md")

private fun File.updateChangelogExpectedDate() {
    changelogExpected.writeText(
        changelogExpected
            .readText()
            .replace("{{ PLACEHOLDER_DATE }}", SimpleDateFormat("yyyy-MM-dd").format(Date()))
    )
}
