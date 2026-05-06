package hubdle.declarative.documentation.readme

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import hubdle.platform.HubdleProperties
import hubdle.platform.LogFixture.lifecycle
import io.kotest.matchers.string.shouldContainInOrder
import io.kotest.matchers.string.shouldNotContain
import kotlin.test.Test

class HubdleDeclarativeDocumentationReadmeTest : GradleTestKitTest() {

    @Test
    fun `GIVEN a project WHEN hubdle documentation readme is enabled THEN readme feature is applied`() {
        gradleTestKitTest("hubdle-config-documentation-readme/basic") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldContainInOrder(
                    lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" },
                    lifecycle("documentation") { "Feature 'documentation' enabled on ':'" },
                    lifecycle("readme") { "Feature 'readme' enabled on ':'" },
                )
        }
    }

    @Test
    fun `GIVEN a project WHEN hubdle disabled THEN readme feature is not applied`() {
        gradleTestKitTest("hubdle-config-documentation-readme/hubdle-disabled") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldNotContain(lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" })
                .shouldNotContain(
                    lifecycle("documentation") { "Feature 'documentation' enabled on ':'" }
                )
                .shouldNotContain(lifecycle("readme") { "Feature 'readme' enabled on ':'" })
        }
    }
}
