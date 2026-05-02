package hubdle.declarative

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import hubdle.platform.HubdleProperties
import hubdle.platform.LogFixture.lifecycle
import io.kotest.matchers.string.shouldContainInOrder
import kotlin.test.Test

internal class HubdleDeclarativeTest : GradleTestKitTest() {

    @Test
    fun `GIVEN a project WHEN hubdle is applied AND logging is enabled THEN hubdle declarative logs are printed`() {
        gradleTestKitTest("main-hubdle") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldContainInOrder(
                    lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" },
                    lifecycle("hubdle") { "Registering 'fixChecks' task" },
                    lifecycle("hubdle") { "Registering 'generate' task" },
                    lifecycle("hubdle") { "Registering 'tests' task" },
                    lifecycle("hubdle") { "Registering 'prepareKotlinIdeaImport' task" },
                )
        }
    }
}
