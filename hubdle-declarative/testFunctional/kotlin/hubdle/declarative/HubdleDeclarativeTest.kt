package hubdle.declarative

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import hubdle.platform.HubdleProperties
import hubdle.platform.LogFixture
import io.kotest.matchers.string.shouldContainInOrder
import kotlin.test.Test

internal class HubdleDeclarativeTest : GradleTestKitTest() {

    @Test
    fun `GIVEN a project WHEN hubdle is applied AND logging is enabled THEN hubdle declarative logs are printed`() {
        gradleTestKitTest("main-hubdle") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldContainInOrder(
                    LogFixture.lifecycle { "Feature 'hubdle' enabled on ':'" },
                    LogFixture.lifecycle { "Registering 'fixChecks' task" },
                    LogFixture.lifecycle { "Registering 'generate' task" },
                    LogFixture.lifecycle { "Registering 'tests' task" },
                    LogFixture.lifecycle { "Registering 'prepareKotlinIdeaImport' task" },
                )
        }
    }
}
