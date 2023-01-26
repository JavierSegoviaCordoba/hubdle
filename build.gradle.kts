import kotlinx.kover.api.DefaultJacocoEngine
import kotlinx.kover.api.KoverProjectConfig

plugins {
    alias(libs.plugins.javiersc.hubdle)
}

hubdle {
    config {
        analysis()
        binaryCompatibilityValidator()
        coverage()
        documentation {
            changelog()
            readme { badges() }
            site()
        }
        nexus()
    }
}

tasks.register("buildItself") {
    val publishToMavenLocalTasks: List<Task> =
        allprojects.mapNotNull { it.tasks.findByName("publishToMavenLocal") }

    for (task in publishToMavenLocalTasks) {
        dependsOn(task)
    }

    doFirst {
        val libsTomlFile = rootDir.resolve("gradle/libs.versions.toml")
        val libsContent =
            libsTomlFile.readLines().joinToString("\n") { line ->
                if (line.startsWith("""hubdle = """")) """hubdle = "$version"""" else line
            }

        libsTomlFile.writeText(libsContent)

        val settingsFile = rootDir.resolve("settings.gradle.kts")
        val settingsContent = settingsFile.readText()
        settingsFile.writeText(settingsContent.replace("// mavenLocal()", "mavenLocal()"))
    }
}

allprojects {
    afterEvaluate {
        extensions.findByType<KoverProjectConfig>()?.engine?.set(DefaultJacocoEngine)
    }
}
