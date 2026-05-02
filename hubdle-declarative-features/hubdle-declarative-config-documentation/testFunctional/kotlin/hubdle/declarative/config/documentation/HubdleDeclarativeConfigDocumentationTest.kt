package hubdle.declarative.config.documentation

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import hubdle.platform.HubdleProperties
import hubdle.platform.LogFixture.lifecycle
import io.kotest.matchers.string.shouldContainInOrder
import io.kotest.matchers.string.shouldNotContain
import kotlin.test.Test

class HubdleDeclarativeConfigDocumentationTest : GradleTestKitTest() {

    @Test
    fun `GIVEN a project WHEN hubdle config documentation is enabled THEN documentation feature is applied`() {
        gradleTestKitTest("hubdle-config-documentation/basic") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldContainInOrder(
                    lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" },
                    lifecycle("config") { "Feature 'config' enabled on ':'" },
                    lifecycle("documentation") { "Feature 'documentation' enabled on ':'" },
                )
        }
    }

    @Test
    fun `GIVEN a project WHEN hubdle config documentation is enabled and hubdle disabled THEN documentation feature is not applied`() {
        gradleTestKitTest("hubdle-config-documentation/hubdle-disabled") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldNotContain(lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" })
                .shouldNotContain(lifecycle("config") { "Feature 'config' enabled on ':'" })
                .shouldNotContain(
                    lifecycle("documentation") { "Feature 'documentation' enabled on ':'" }
                )
        }
    }
}
