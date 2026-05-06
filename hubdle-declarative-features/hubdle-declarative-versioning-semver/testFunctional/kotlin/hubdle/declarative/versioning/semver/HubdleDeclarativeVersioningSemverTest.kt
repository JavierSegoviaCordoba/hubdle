package hubdle.declarative.versioning.semver

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import hubdle.platform.HubdleProperties
import hubdle.platform.LogFixture.lifecycle
import io.kotest.matchers.file.shouldBeAFile
import io.kotest.matchers.file.shouldNotExist
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContainInOrder
import io.kotest.matchers.string.shouldNotContain
import kotlin.test.Test

class HubdleDeclarativeVersioningSemverTest : GradleTestKitTest() {

    @Test
    fun `GIVEN a project WHEN hubdle versioning semver is enabled THEN semver configures project versioning`() {
        gradleTestKitTest("hubdle-versioning-semver/basic") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldContainInOrder(
                    lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" },
                    lifecycle("versioning") { "Feature 'versioning' enabled on ':'" },
                    lifecycle("semver") { "Feature 'semver' enabled on ':'" },
                    "There is no git directory",
                )

            projectDir.resolve("build/semver/version.txt").apply {
                shouldBeAFile()
                readLines().shouldBe(listOf("[undefined]", "v[undefined]"))
            }
        }
    }

    @Test
    fun `GIVEN a project WHEN hubdle disabled THEN semver feature is not applied`() {
        gradleTestKitTest("hubdle-versioning-semver/hubdle-disabled") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldNotContain(lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" })
                .shouldNotContain(lifecycle("versioning") { "Feature 'versioning' enabled on ':'" })
                .shouldNotContain(lifecycle("semver") { "Feature 'semver' enabled on ':'" })

            projectDir.resolve("build/semver/version.txt").shouldNotExist()
        }
    }
}
