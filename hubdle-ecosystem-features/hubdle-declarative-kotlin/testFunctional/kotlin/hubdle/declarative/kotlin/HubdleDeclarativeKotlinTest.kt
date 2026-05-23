package hubdle.declarative.kotlin

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import hubdle.platform.HubdleProperties
import hubdle.platform.LogFixture.lifecycle
import io.kotest.matchers.string.shouldContainInOrder
import io.kotest.matchers.string.shouldNotContain
import kotlin.test.Test

class HubdleDeclarativeKotlinTest : GradleTestKitTest() {

    @Test
    fun `GIVEN a project WHEN hubdle kotlin is enabled THEN kotlin feature is applied`() {
        gradleTestKitTest("hubdle-kotlin/basic") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldContainInOrder(
                    lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" },
                    lifecycle("kotlin") { "Feature 'kotlin' enabled on ':'" },
                )
        }
    }

    @Test
    fun `GIVEN a project WHEN hubdle kotlin is enabled and hubdle disabled THEN kotlin feature is not applied`() {
        gradleTestKitTest("hubdle-kotlin/hubdle-disabled") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldNotContain(lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" })
                .shouldNotContain(lifecycle("kotlin") { "Feature 'kotlin' enabled on ':'" })
        }
    }
}
