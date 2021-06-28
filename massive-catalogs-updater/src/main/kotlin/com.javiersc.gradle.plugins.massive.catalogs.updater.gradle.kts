import com.javiersc.semanticVersioning.Version
import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

val url = "https://repo1.maven.org/maven2/com/javiersc/massive-catalogs"

val reset = "\u001B[0m"
val magenta = "\u001b[1;35m"
val yellow = "\u001B[0;33m"
val red = "\u001b[0;31m"

val errorMessage = "${red}There was a problem fetching Massive Catalogs version$reset"

tasks.register("updateMassiveCatalogs") {
    group = "updater"
    description = "Check the latest Massive Catalogs version"

    doLast {
        val projects = getAllProjects()
        check(projects.isNotEmpty()) { errorMessage }

        val version = projects.map { project -> getProjectVersion(project) }.distinct().maxOrNull()
        check(version != null) { errorMessage }

        writeVersionToGradleProperties(version)
    }
}

fun getAllProjects(): List<String> {
    val links: Elements = Jsoup.connect(url).get().body().select("a[href]")

    return links.mapNotNull { element: Element ->
        if (!element.text().contains("../")) element.text().removeSuffix("/") else null
    }
}

fun getProjectVersion(project: String): Version {
    val links: Elements = Jsoup.connect("$url/$project").get().body().select("a[href]")
    val version =
        links
            .mapNotNull { element: Element ->
                if (element.text().first().isDigit()) Version(element.text().removeSuffix("/"))
                else null
            }
            .maxByOrNull { it }

    check(version != null) { errorMessage }

    return version
}

fun writeVersionToGradleProperties(version: Version) {
    logger.lifecycle(
        "${magenta}Latest Massive Catalogs version: $yellow${version.value}$reset"
    )
    val gradleProperties: File = file("${rootProject.rootDir.path}/gradle.properties")

    gradleProperties.apply {
        writeText(
            readLines().joinToString("\n") { line ->
                if (line.startsWith("massiveCatalogs=")) {
                    "massiveCatalogs=${version.value}"
                } else line
            } + "\n"
        )
    }

    file("${rootProject.buildDir}/versions/massive-catalogs.txt").apply {
        ensureParentDirsCreated()
        createNewFile()
        writeText(version.value)
    }
}
