package hubdle.declarative.config

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import hubdle.platform.HubdleProperties
import hubdle.platform.LogFixture.lifecycle
import io.kotest.matchers.string.shouldContainInOrder
import io.kotest.matchers.string.shouldNotContain
import kotlin.test.Test

class HubdleDeclarativeConfigTest : GradleTestKitTest() {

    @Test
    fun `GIVEN a project WHEN hubdle config is enabled THEN config feature is applied`() {
        gradleTestKitTest("hubdle-config/basic") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldContainInOrder(
                    lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" },
                    lifecycle("hubdle") { "Registering 'fixChecks' task" },
                    lifecycle("hubdle") { "Registering 'generate' task" },
                    lifecycle("hubdle") { "Registering 'tests' task" },
                    lifecycle("hubdle") { "Registering 'prepareKotlinIdeaImport' task" },
                    lifecycle("config") { "Feature 'config' enabled on ':'" },
                    lifecycle("config") { "Project ':' has changed the group to 'foo-some'" },
                )
        }
    }

    @Test
    fun `GIVEN a project WHEN hubdle config is enabled and hubdle disabled THEN config feature is not applied`() {
        gradleTestKitTest("hubdle-config/hubdle-disabled") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldNotContain(lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" })
                .shouldNotContain(lifecycle("config") { "Feature 'config' enabled on ':'" })
        }
    }
}
