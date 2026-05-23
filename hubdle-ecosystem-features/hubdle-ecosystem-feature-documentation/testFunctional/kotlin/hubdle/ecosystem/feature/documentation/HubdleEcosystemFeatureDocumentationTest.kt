package hubdle.ecosystem.feature.documentation

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import hubdle.platform.HubdleProperties
import hubdle.platform.LogFixture.lifecycle
import io.kotest.matchers.string.shouldContainInOrder
import io.kotest.matchers.string.shouldNotContain
import kotlin.test.Test

class HubdleEcosystemFeatureDocumentationTest : GradleTestKitTest() {

    @Test
    fun `GIVEN a project WHEN hubdle documentation is enabled THEN documentation feature is applied`() {
        gradleTestKitTest("hubdle-feature-documentation/basic") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldContainInOrder(
                    lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" },
                    lifecycle("documentation") { "Feature 'documentation' enabled on ':'" },
                )
        }
    }

    @Test
    fun `GIVEN a project WHEN hubdle documentation is enabled and hubdle disabled THEN documentation feature is not applied`() {
        gradleTestKitTest("hubdle-feature-documentation/hubdle-disabled") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldNotContain(lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" })
                .shouldNotContain(
                    lifecycle("documentation") { "Feature 'documentation' enabled on ':'" }
                )
        }
    }
}
