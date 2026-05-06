package hubdle.declarative.analysis.sonar

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import hubdle.platform.HubdleProperties
import hubdle.platform.LogFixture.lifecycle
import io.kotest.matchers.string.shouldContainInOrder
import io.kotest.matchers.string.shouldNotContain
import kotlin.test.Test

class HubdleDeclarativeAnalysisSonarTest : GradleTestKitTest() {

    @Test
    fun `GIVEN a project WHEN hubdle analysis sonar is enabled THEN sonar feature is applied`() {
        gradleTestKitTest("hubdle-config-analysis-sonar/basic", debug = true) {
            gradlew(
                    "buildEnvironment",
                    "-P",
                    "${HubdleProperties.Logging.Enabled}=true",
                    "--no-scan",
                )
                .output
                .shouldContainInOrder(
                    lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" },
                    lifecycle("analysis") { "Feature 'analysis' enabled on ':'" },
                    lifecycle("sonar") { "Feature 'sonar' enabled on ':'" },
                )
        }
    }

    @Test
    fun `GIVEN a project WHEN hubdle disabled THEN sonar feature is not applied`() {
        gradleTestKitTest("hubdle-config-analysis-sonar/hubdle-disabled") {
            gradlew(
                    "buildEnvironment",
                    "-P",
                    "${HubdleProperties.Logging.Enabled}=true",
                    "--no-scan",
                )
                .output
                .shouldNotContain(lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" })
                .shouldNotContain(lifecycle("analysis") { "Feature 'analysis' enabled on ':'" })
                .shouldNotContain(lifecycle("sonar") { "Feature 'sonar' enabled on ':'" })
        }
    }
}
