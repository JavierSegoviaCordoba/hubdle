import com.javiersc.gradle.properties.extensions.getPropertyOrNull

plugins { //
    alias(hubdle.plugins.javiersc.hubdle)
}

hubdle {
    config {
        analysis()
        binaryCompatibilityValidator()
        coverage()
        documentation {
            api()
            changelog()
            readme { badges() }
            site()
        }
        nexus()
        versioning {
            semver {
                // TODO: https://github.com/gradle-nexus/publish-plugin/issues/84
                tagPrefix.set(provider { getPropertyOrNull("semver.tagPrefix") ?: "" })
            }
        }
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
