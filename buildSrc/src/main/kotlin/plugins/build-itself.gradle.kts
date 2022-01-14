tasks.register("buildItself") {
    val publishToMavenLocalTasks: List<Task> =
        allprojects.mapNotNull { it.tasks.findByName("publishToMavenLocal") }

    for (task in publishToMavenLocalTasks) {
        dependsOn(task)
    }

    doFirst {
        file("${rootProject.rootDir}/gradle/pluginLibs.toml").apply {
            val content =
                readLines().joinToString("\n") { line ->
                    if (line.startsWith("""javierscGradlePlugins = """")) {
                        """javierscGradlePlugins = "${project.version}""""
                    } else {
                        line
                    }
                }

            writeText(content)
        }

        file("${rootProject.rootDir}/buildSrc/build.gradle.kts").apply {
            val content =
                readLines().joinToString("\n") { line ->
                    if (line.startsWith("repositories {")) {
                        "$line\n    mavenLocal()"
                    } else {
                        line
                    }
                }
            writeText(content)
        }
    }
}
