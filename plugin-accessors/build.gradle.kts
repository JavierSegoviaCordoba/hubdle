plugins {
    kotlin("jvm")
    publish
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

dependencies { implementation(gradleApi()) }

file("${project.projectDir.path}/MODULE.md").apply {
    val listHeaderIndex = readLines().indexOfFirst { line -> line.contains("### List of plugins") }
    val filteredContent = readLines().dropLast(readLines().count() - listHeaderIndex - 1)
    val plugins =
        file("${project.projectDir.path}/src/main/kotlin/PluginAccessors.kt")
            .readLines()
            .mapNotNull { line ->
                if (line.contains("PluginDependenciesSpec.`")) {
                    "- ${line.replaceBefore("`", "").replaceAfterLast("`", "")}"
                } else null
            }
            .distinct()

    writeText((filteredContent + "" + plugins + "").joinToString("\n"))
}
