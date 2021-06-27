import com.google.gson.Gson
import com.javiersc.semanticVersioning.Version
import java.net.HttpURLConnection
import java.net.URL
import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated

tasks.register("updateMassiveCatalogs") {
    group = "updater"
    description = "Check the latest Massive Catalogs version"

    doLast {
        val reset = "\u001B[0m"
        val magenta = "\u001b[1;35m"
        val yellow = "\u001B[0;33m"
        val red = "\u001b[0;31m"

        val url =
            "https://search.maven.org/solrsearch/select?q=g:%22com.javiersc.massive-catalogs%22&wt=json"

        (URL(url).openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            if (responseCode !in 200..299) {
                error(
                    "${red}Error getting the latest Massive Catalogs version (code: $responseCode)$reset"
                )
            }

            inputStream.bufferedReader().use {
                val mavenSearch: MavenSearch = Gson().fromJson(it.readText(), MavenSearch::class.java)
                val latestVersion: String? =
                    mavenSearch
                        .response
                        .docs
                        .distinct()
                        .maxByOrNull { doc -> Version(doc.latestVersion) }
                        ?.latestVersion

                check(latestVersion != null && latestVersion.isNotBlank()) {
                    "${red}Latest Massive Catalogs version not found$reset"
                }

                logger.lifecycle(
                    "${magenta}Latest Massive Catalogs version: $yellow$latestVersion$reset"
                )

                val gradleProperties: File = file("${rootProject.rootDir.path}/gradle.properties")

                gradleProperties.apply {
                    writeText(
                        readLines().joinToString("\n") { line ->
                            if (line.startsWith("massiveCatalogs=")) "massiveCatalogs=$latestVersion"
                            else line
                        }
                    )
                }

                file("${rootProject.buildDir}/versions/massive-catalogs.txt").apply {
                    ensureParentDirsCreated()
                    createNewFile()
                    writeText(latestVersion)
                }
            }
        }
    }
}

data class MavenSearch(val response: Response)

data class Response(val docs: List<Doc>)

data class Doc(val latestVersion: String)
