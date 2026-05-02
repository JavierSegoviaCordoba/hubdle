package hubdle.declarative.config.analysis

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import hubdle.platform.HubdleProperties
import hubdle.platform.LogFixture.lifecycle
import io.kotest.matchers.string.shouldContainInOrder
import io.kotest.matchers.string.shouldNotContain
import kotlin.test.Test

class HubdleDeclarativeConfigAnalysisTest : GradleTestKitTest() {

    @Test
    fun `GIVEN a project WHEN hubdle config analysis is enabled THEN analysis feature is applied`() {
        gradleTestKitTest("hubdle-config-analysis/basic") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldContainInOrder(
                    lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" },
                    lifecycle("config") { "Feature 'config' enabled on ':'" },
                    lifecycle("analysis") { "Feature 'analysis' enabled on ':'" },
                )
        }
    }

    @Test
    fun `GIVEN a project WHEN hubdle config analysis is enabled and hubdle disabled THEN analysis feature is not applied`() {
        gradleTestKitTest("hubdle-config-analysis/hubdle-disabled") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldNotContain(lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" })
                .shouldNotContain(lifecycle("config") { "Feature 'config' enabled on ':'" })
                .shouldNotContain(lifecycle("analysis") { "Feature 'analysis' enabled on ':'" })
        }
    }
}
