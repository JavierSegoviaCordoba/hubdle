package hubdle.declarative.documentation.readme.badges

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import com.javiersc.kotlin.stdlib.resource
import hubdle.platform.HubdleProperties
import hubdle.platform.LogFixture.lifecycle
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContainInOrder
import io.kotest.matchers.string.shouldNotContain
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream
import kotlin.test.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class HubdleDeclarativeDocumentationReadmeBadgesTest : GradleTestKitTest() {

    @Test
    fun `GIVEN a project WHEN hubdle documentation readme badges is enabled THEN badges feature is applied`() {
        gradleTestKitTest("hubdle-config-documentation-readme-badges/basic") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldContainInOrder(
                    lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" },
                    lifecycle("documentation") { "Feature 'documentation' enabled on ':'" },
                    lifecycle("readme") { "Feature 'readme' enabled on ':'" },
                    lifecycle("badges") { "Feature 'badges' enabled on ':'" },
                )
        }
    }

    @Test
    fun `GIVEN a project WHEN hubdle disabled THEN badges feature is not applied`() {
        gradleTestKitTest("hubdle-config-documentation-readme-badges/hubdle-disabled") {
            gradlew("build", "-P", "${HubdleProperties.Logging.Enabled}=true", "--no-scan")
                .output
                .shouldNotContain(lifecycle("hubdle") { "Feature 'hubdle' enabled on ':'" })
                .shouldNotContain(
                    lifecycle("documentation") { "Feature 'documentation' enabled on ':'" }
                )
                .shouldNotContain(lifecycle("readme") { "Feature 'readme' enabled on ':'" })
                .shouldNotContain(lifecycle("badges") { "Feature 'badges' enabled on ':'" })
        }
    }

    @ParameterizedTest
    @MethodSource("provideReadmeBadgeSnapshots")
    fun `GIVEN readme badges snapshot WHEN writeReadmeBadges THEN readme is created`(
        snapshot: String
    ) {
        val sandboxPath = "hubdle-config-documentation-readme-badges/snapshots/$snapshot"
        gradleTestKitTest(sandboxPath = sandboxPath, debug = true) {
            withArgumentsFromTXT()
            build()

            val expect: File = projectDir.resolve("README.expect.md")
            val actual: File = projectDir.resolve("README.md")

            val actualKotlinVersion =
                actual.readLines().first().substringAfter("kotlin-").substringBefore("-blueviolet")

            expect.apply {
                val updatedText = readText().replace("{VERSION}", actualKotlinVersion)
                writeText(updatedText)
            }
            actual.readText().shouldBe(expect.readText())
        }
    }

    companion object {

        @JvmStatic
        fun provideReadmeBadgeSnapshots(): Stream<String> {
            val snapshotsFile = resource("hubdle-config-documentation-readme-badges/snapshots")
            val snapshotsPath = Paths.get(snapshotsFile.toURI())
            val snapshots =
                Files.list(snapshotsPath)
                    .filter(Files::isDirectory)
                    .map { it.fileName.toString() }
                    .sorted()
            return snapshots
        }
    }
}
