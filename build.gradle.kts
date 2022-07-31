buildscript {
    dependencies {
        classpath(libs.android.toolsBuild.gradle)
        classpath(libs.jetbrains.compose.composeGradlePlugin)
        classpath(libs.jetbrains.kotlin.kotlinGradlePlugin)
    }
}

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
