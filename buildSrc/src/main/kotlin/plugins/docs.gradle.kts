import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper

plugins {
    id("org.jetbrains.dokka")
}

tasks {
    withType<DokkaTaskPartial>() {
        dokkaSourceSets.configureEach { includes.from(listOf("MODULE.md")) }
    }

    withType<DokkaMultiModuleTask>().configureEach {
        val dokkaDir = buildDir.resolve("dokka")
        outputDirectory.set(dokkaDir)
    }
}

allprojects {
    afterEvaluate {
        if (plugins.asSequence().mapNotNull { (it as? KotlinBasePluginWrapper) }.count() > 0) {
            plugins.apply("docs")
        }
    }
}
