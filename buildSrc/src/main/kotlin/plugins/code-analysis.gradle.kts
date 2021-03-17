import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
}

detekt {
    failFast = false
    parallel = true
    isIgnoreFailures = true
    autoCorrect = false
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

internal val Project.detektVersion: String
    get() = pluginLibsAccessors.versions.detekt.get()
