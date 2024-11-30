package hubdle.declarative.config.documentation.readme.badges

import com.javiersc.gradle.testkit.test.extensions.GradleTestKitTest
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.Test

class ReadmeBadgesProjectPluginTest : GradleTestKitTest() {

    @Test
    fun `GIVEN a readme with the mavenCentral, snapshots, and build badges, WHEN writeReadmeBadges task runs, THEN adds the badges to the readme file`() {
        gradleTestKitTest(sandboxPath = "simple-a") {
            gradlew("writeReadmeBadges").run {
                val libraryDir: File = projectDir.resolve("library-a")

                val actualReadmeFile: File = libraryDir.resolve("README.md")
                val actualContent: String = actualReadmeFile.readText()

                val expectedFile: File = libraryDir.resolve("README_EXPECTED.md")
                val expectedContent: String = expectedFile.readText()

                assertTrue { actualReadmeFile.exists() }
                assertTrue { actualContent.isNotBlank() }
                assertTrue { expectedFile.exists() }
                assertTrue { expectedContent.isNotBlank() }
                assertEquals(expected = expectedContent, actual = actualContent)
            }
        }
    }
}
