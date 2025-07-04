import com.javiersc.gradle.properties.extensions.getStringProperty

plugins { //
    alias(hubdle.plugins.javiersc.hubdle)
}

val isGradlePlugin = getStringProperty("semver.tagPrefix").orNull == "p"

hubdle {
    config {
        analysis()
        binaryCompatibilityValidator()
        coverage()
        documentation {
            api()
            changelog()
            readme { //
                badges()
            }
            site()
        }
    }
}

tasks.named("patchChangelog").configure { //
    onlyIf { isGradlePlugin }
}

tasks.named("addChangelogItem").configure { //
    onlyIf { isGradlePlugin }
}

tasks.register("buildItself") {
    val publishToMavenLocalTasks: List<Task> =
        allprojects.mapNotNull { it.tasks.findByName("publishToMavenLocal") }

    for (task: Task in publishToMavenLocalTasks) {
        dependsOn(task)
    }

    doFirst {
        val libsTomlFile: File = rootDir.resolve("gradle/libs.versions.toml")
        val libsContent: String =
            libsTomlFile.readLines().joinToString("\n") { line ->
                if (line.startsWith("""hubdle = """")) """hubdle = "$version"""" else line
            }

        libsTomlFile.writeText(libsContent)

        val settingsFile: File = rootDir.resolve("settings.gradle.kts")
        val settingsContent: String = settingsFile.readText()
        settingsFile.writeText(settingsContent.replace("// mavenLocal()", "mavenLocal()"))
    }
}
