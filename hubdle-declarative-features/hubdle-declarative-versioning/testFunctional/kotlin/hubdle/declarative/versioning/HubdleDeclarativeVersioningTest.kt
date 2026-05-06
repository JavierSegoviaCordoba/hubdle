package hubdle.declarative.versioning

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import hubdle.platform.HubdleProperties
import hubdle.platform.LogFixture.lifecycle
import io.kotest.matchers.string.shouldContainInOrder
import io.kotest.matchers.string.shouldNotContain
import kotlin.test.Test

class HubdleDeclarativeVersioningTest : GradleTestKitTest() {

    @Test
    fun `GIVEN a project WHEN hubdle versioning is enabled THEN versioning namespace is applied`() {
        gradleTestKitTest("hubdle-config-versioning/basic") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldContainInOrder(
                    lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" },
                    lifecycle("versioning") { "Feature 'versioning' enabled on ':'" },
                )
        }
    }

    @Test
    fun `GIVEN a project WHEN hubdle versioning is enabled and hubdle disabled THEN versioning namespace is not applied`() {
        gradleTestKitTest("hubdle-config-versioning/hubdle-disabled") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldNotContain(lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" })
                .shouldNotContain(lifecycle("versioning") { "Feature 'versioning' enabled on ':'" })
        }
    }
}
