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
