package hubdle.ecosystem.feature.install

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import hubdle.platform.HubdleProperties
import hubdle.platform.LogFixture.lifecycle
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.string.shouldContainInOrder
import io.kotest.matchers.string.shouldNotContain
import kotlin.test.Test
import org.gradle.testkit.runner.GradleRunner

@Suppress("FunctionName")
class HubdleEcosystemFeatureInstallTest : GradleTestKitTest() {

    @Test
    fun `GIVEN a project WHEN hubdle install is enabled THEN install feature is applied`() {
        gradleTestKitTest("hubdle-feature-install/basic") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldContainInOrder(
                    lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" },
                    lifecycle("install") { "Feature 'install' enabled on ':'" },
                )
        }
    }

    @Test
    fun `GIVEN a project WHEN hubdle install preCommits is configured THEN install feature is applied`() {
        gradleTestKitTest("hubdle-feature-install/pre-commits") {
            gradlew(
                    "installPreCommit",
                    "-P",
                    "${HubdleProperties.Logging.Enabled}=true",
                    "--no-scan",
                )
                .output
                .shouldContainInOrder(
                    lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" },
                    lifecycle("install") { "Feature 'install' enabled on ':'" },
                )

            this.preCommitFileLines.shouldContain("  .git/hooks/.hubdle/pre-commit")
            this.preCommitFileLines.shouldContain("if [ -f .git/hooks/.hubdle/pre-commit ]; then")
            this.preCommitFileLines.shouldContain("fi")
            this.preCommitHubdleFileLines.shouldContain("./gradlew tests")
            this.preCommitHubdleFileLines.shouldContain("./gradlew applyFormat")
            this.preCommitHubdleFileLines.shouldContain("./gradlew assemble")
            this.preCommitHubdleFileLines.shouldContain("./gradlew checkAnalysis")
            this.preCommitHubdleFileLines.shouldNotContain("./gradlew checkApi")
            this.preCommitHubdleFileLines.shouldContain("./gradlew checkFormat")
            this.preCommitHubdleFileLines.shouldContain("./gradlew dumpApi")
        }
    }

    @Test
    fun `GIVEN a project WHEN hubdle install preCommits all are enabled THEN checkApi is present`() {
        gradleTestKitTest("hubdle-feature-install/pre-commits-all-enabled") {
            gradlew("installPreCommit", "--no-scan")
            this.preCommitFileLines.shouldContain("  .git/hooks/.hubdle/pre-commit")
            this.preCommitFileLines.shouldContain("if [ -f .git/hooks/.hubdle/pre-commit ]; then")
            this.preCommitFileLines.shouldContain("fi")
            this.preCommitHubdleFileLines.shouldContain("./gradlew tests")
            this.preCommitHubdleFileLines.shouldContain("./gradlew applyFormat")
            this.preCommitHubdleFileLines.shouldContain("./gradlew assemble")
            this.preCommitHubdleFileLines.shouldContain("./gradlew checkAnalysis")
            this.preCommitHubdleFileLines.shouldContain("./gradlew checkApi")
            this.preCommitHubdleFileLines.shouldContain("./gradlew checkFormat")
            this.preCommitHubdleFileLines.shouldContain("./gradlew dumpApi")
        }
    }

    @Test
    fun `GIVEN a project WHEN hubdle disabled THEN install feature is not applied`() {
        gradleTestKitTest("hubdle-feature-install/hubdle-disabled") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldNotContain(lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" })
                .shouldNotContain(lifecycle("install") { "Feature 'install' enabled on ':'" })
        }
    }

    private val GradleRunner.preCommitFileLines: List<String>
        get() = projectDir.resolve(".git/hooks/pre-commit").readLines()

    private val GradleRunner.preCommitHubdleFileLines: List<String>
        get() = projectDir.resolve(".git/hooks/.hubdle/pre-commit").readLines()
}
