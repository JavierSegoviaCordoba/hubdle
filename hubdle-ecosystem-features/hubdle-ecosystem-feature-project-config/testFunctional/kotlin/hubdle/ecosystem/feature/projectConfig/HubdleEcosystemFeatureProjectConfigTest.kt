package hubdle.ecosystem.feature.projectConfig

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import hubdle.platform.HubdleProperties
import hubdle.platform.LogFixture.lifecycle
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldContainInOrder
import io.kotest.matchers.string.shouldNotContain
import kotlin.test.Test

class HubdleEcosystemFeatureProjectConfigTest : GradleTestKitTest() {

    @Test
    fun `GIVEN a project WHEN hubdle project config is enabled THEN project config feature is applied`() {
        gradleTestKitTest("hubdle-feature-project-config/basic") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldContainInOrder(
                    lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" },
                    lifecycle("hubdle") { "Registering 'fixChecks' task" },
                    lifecycle("hubdle") { "Registering 'generate' task" },
                    lifecycle("hubdle") { "Registering 'tests' task" },
                    lifecycle("hubdle") { "Registering 'prepareKotlinIdeaImport' task" },
                    lifecycle("projectConfig") { "Feature 'projectConfig' enabled on ':'" },
                    lifecycle("projectConfig") {
                        "Project ':' has changed the group to 'foo-some'"
                    },
                )
            gradlew("tasks", "--all", "--no-scan")
                .output
                .shouldContain("fixChecks")
                .shouldContain("generate")
                .shouldContain("tests")
                .shouldContain("prepareKotlinIdeaImport")
        }
    }

    @Test
    fun `GIVEN a project WHEN hubdle project config is enabled and hubdle disabled THEN project config feature is not applied`() {
        gradleTestKitTest("hubdle-feature-project-config/hubdle-disabled") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldNotContain(lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" })
                .shouldNotContain(
                    lifecycle("projectConfig") { "Feature 'projectConfig' enabled on ':'" }
                )
        }
    }
}
