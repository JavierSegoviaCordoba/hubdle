import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension

plugins { id("io.gitlab.arturbosch.detekt") }

configure<DetektExtension> {
    failFast = false
    parallel = true
    isIgnoreFailures = true
    buildUponDefaultConfig = true
    basePath = rootProject.projectDir.path
}

tasks.withType<Detekt>().configureEach {
    setSource(files(projectDir))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")

    reports {
        html { enabled = true }
        sarif { enabled = true }
        txt { enabled = false }
        xml { enabled = false }
    }
}

val ideaDir = file("$rootDir/.idea").also(File::mkdirs)

file("${ideaDir.path}/detekt.xml").apply {
    if (!exists()) {
        createNewFile()
        writeText(detektXmlContent)
    }
}

val detektXmlContent: String
    get() =
        """
            <?xml version="1.0" encoding="UTF-8"?>
            <project version="4">
              <component name="DetektProjectConfiguration">
                <enableDetekt>true</enableDetekt>
                <enableFormatting>true</enableFormatting>
                <buildUponDefaultConfig>true</buildUponDefaultConfig>
              </component>
            </project>
        """.trimIndent()
