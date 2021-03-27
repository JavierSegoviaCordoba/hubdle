import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("org.jetbrains.dokka")
}

tasks {
    withType<DokkaTask>() {
        dokkaSourceSets.configureEach {
            includes.from(listOf("MODULE.md"))
        }
    }

    withType<DokkaMultiModuleTask>().configureEach {
        val dokkaDir = buildDir.resolve("dokka")
        outputDirectory.set(dokkaDir)
    }
}
