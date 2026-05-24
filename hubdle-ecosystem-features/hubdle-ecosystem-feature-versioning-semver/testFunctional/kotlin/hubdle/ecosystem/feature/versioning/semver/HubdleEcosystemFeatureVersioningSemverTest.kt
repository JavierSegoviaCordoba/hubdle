package hubdle.ecosystem.feature.versioning.semver

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import io.kotest.matchers.string.shouldContain
import kotlin.test.Test

class HubdleEcosystemFeatureVersioningSemverTest : GradleTestKitTest() {

    @Test
    fun `GIVEN semver is enabled WHEN assembling THEN tag prefix is v`() {
        gradleTestKitTest("hubdle-feature-versioning-semver/basic") {
            projectDir.resolve("_git").renameTo(projectDir.resolve(".git"))
            gradlew("assemble", "--no-scan")
                .output
                .shouldContain("semver for versioning-semver-project: v1.0.0")
        }
    }
}
