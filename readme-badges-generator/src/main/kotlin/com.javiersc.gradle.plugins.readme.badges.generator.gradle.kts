@file:OptIn(ExperimentalStdlibApi::class)

import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

val shieldsIoUrl
    get() = "https://img.shields.io"

val libGroup: String
    get() = property("libGroup")!!.toString()
val libName: String
    get() = property("libName")!!.toString()

val libFolderUrl: String
    get() = "$libGroup/$libName".replace(".", "/")

val repoUrl: String
    get() = property("pomSmcUrl")!!.toString()
val repoWithoutUrlPrefix: String
    get() = repoUrl.replace("https://github.com/", "")

fun buildKotlinVersionBadge(): String {
    val kotlinVersion =
        allprojects
            .asSequence()
            .map { project -> project.getKotlinPluginVersion() }
            .toSet()
            .filterNotNull()
            .firstOrNull()

    checkNotNull(kotlinVersion) { "Kotlin Gradle plugin is not being applied to any project" }

    return "![Kotlin version]" +
        "($shieldsIoUrl/badge/kotlin-$kotlinVersion-blueviolet" +
        "?logo=kotlin&logoColor=white)"
}

fun buildMavenRepoBadge(subproject: String, mavenRepo: MavenRepo): String {
    val label: String =
        if (properties["shouldGenerateVersionBadgePerProject"]?.toString()?.toBoolean() == true) {
            "$subproject - ${mavenRepo.name}"
        } else {
            mavenRepo.name
        }

    val labelPath = label.replace(" ", "%20")

    return if (label.contains("snapshot", ignoreCase = true)) {
        "[![$label]" +
            "($shieldsIoUrl/nexus/s/$libGroup.$libName/$subproject" +
            "?server=https%3A%2F%2Foss.sonatype.org%2F" +
            "&label=$labelPath)]" +
            "(https://oss.sonatype.org/content/repositories/snapshots/$libFolderUrl/$subproject/)"
    } else {
        "[![$label]" +
            "($shieldsIoUrl/maven-central/v/$libGroup.$libName/$subproject" +
            "?label=$labelPath)]" +
            "(https://repo1.maven.org/maven2/$libFolderUrl/$subproject/)"
    }
}

/** Sonatype Maven repos */
enum class MavenRepo {
    MavenCentral,
    Snapshot
}

fun buildBuildBadge(): String {
    return "[![Build]" +
        "($shieldsIoUrl/github/workflow/status/$repoWithoutUrlPrefix/build" +
        "?label=Build&logo=GitHub)]" +
        "($repoUrl/tree/main)"
}

fun buildAnalysisBadge(sonar: Sonar): String {
    val projectId = repoWithoutUrlPrefix.replace("/", "_")
    return "[![${sonar.label}]" +
        "($shieldsIoUrl/sonar/${sonar.path}/$projectId" +
        "?label=${sonar.label.replace(" ", "%20")}&logo=SonarCloud" +
        "&logoColor=white&server=https%3A%2F%2Fsonarcloud.io)]" +
        "(https://sonarcloud.io/dashboard?id=$projectId)"
}

enum class Sonar(val label: String, val path: String) {
    Quality("Quality", "quality_gate"),
    TechDebt("Tech debt", "tech_debt")
}

fun buildReadmeBadges(): List<String> = buildList {
    add(buildKotlinVersionBadge())

    if (properties["shouldGenerateVersionBadgePerProject"]?.toString()?.toBoolean() == true) {
        rootProject.subprojects.onEach {
            add("")
            add(buildMavenRepoBadge(it.name, MavenRepo.MavenCentral))
            add(buildMavenRepoBadge(it.name, MavenRepo.Snapshot))
        }
    } else {

        rootProject.subprojects.firstOrNull { project ->
            properties["mainSubProject"]?.toString()?.let(project.name::contains) ?: false
        }

        val mainSubProjectName: String? =
            rootProject.subprojects
                .firstOrNull { project ->
                    properties["mainSubProject"]?.toString()?.let(project.name::contains) ?: false
                }
                ?.name

        if (mainSubProjectName != null) {
            add(buildMavenRepoBadge(mainSubProjectName, MavenRepo.MavenCentral))
            add(buildMavenRepoBadge(mainSubProjectName, MavenRepo.Snapshot))
        } else println("mainSubProject property not found or the subproject does not exist")
    }

    add("")
    add(buildBuildBadge())
    add(buildAnalysisBadge(Sonar.Quality))
    add(buildAnalysisBadge(Sonar.TechDebt))
    add("")
}

rootProject.tasks.register("generateReadmeBadges") {
    file("${rootProject.projectDir}/README.md").apply {
        val content: List<String> = readLines()
        val updatedContent: List<String> = buildList {
            addAll(buildReadmeBadges())
            addAll(
                content.subList(content.indexOfFirst { it.contains("# ") }, content.lastIndex + 1)
            )
            add("")
        }

        writeText(updatedContent.joinToString(separator = "\n"))
    }
}
