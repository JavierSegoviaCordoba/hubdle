package com.javiersc.gradle.plugins.readme.badges.generator

import com.javiersc.gradle.plugins.core.removeDuplicateEmptyLines
import java.io.File
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

abstract class ReadmeBadgesGeneratorPlugin : Plugin<Project> {

    @OptIn(ExperimentalStdlibApi::class)
    override fun apply(target: Project) {
        ReadmeBadgesGeneratorExtension.createExtension(target)

        target.rootProject.tasks.register("buildReadmeBadges") { task ->
            task.group = "documentation"

            task.doLast {
                File("${target.rootProject.projectDir}/README.md").apply {
                    val content: List<String> = readLines()
                    val updatedContent: List<String> = buildList {
                        addAll(target.buildReadmeBadges())
                        addAll(
                            content.subList(
                                content.indexOfFirst { it.contains("# ") },
                                content.lastIndex + 1
                            )
                        )
                        add("")
                    }

                    writeText(updatedContent.joinToString(separator = "\n"))
                }
            }
        }
    }
}

/** Sonatype Maven repos */
private enum class MavenRepo {
    MavenCentral,
    Snapshot
}

/** Sonar metrics */
private enum class Sonar(val label: String, val path: String) {
    Coverage("Coverage", "coverage"),
    Quality("Quality", "quality_gate"),
    TechDebt("Tech debt", "tech_debt"),
}

private const val shieldsIoUrl = "https://img.shields.io"

private val Project.projectGroup: String
    get() =
        checkNotNull(properties["allProjects.group"]) {
                "`allProjects.group` in `gradle.properties` is missing"
            }
            .toString()
private val Project.projectName: String
    get() =
        checkNotNull(properties["allProjects.name"]) {
                "a`llProjects.name` in `gradle.properties` is missing"
            }
            .toString()

private val Project.libFolderUrl: String
    get() = "$projectGroup/$projectName".replace(".", "/")

private val Project.repoUrl: String
    get() =
        checkNotNull(property("pom.smc.url")?.toString()) {
            "`pom.smc.url` in `gradle.properties` is missing"
        }
private val Project.repoWithoutUrlPrefix: String
    get() = repoUrl.replace("https://github.com/", "")

private fun Project.buildKotlinVersionBadge(): String {
    val kotlinVersion =
        allprojects
            .asSequence()
            .map { project -> project.getKotlinPluginVersion() }
            .toSet()
            .firstOrNull()

    checkNotNull(kotlinVersion) { "Kotlin Gradle plugin is not being applied to any project" }

    logger.lifecycle("Kotlin version: $kotlinVersion")

    return "![Kotlin version]" +
        "($shieldsIoUrl/badge/kotlin-$kotlinVersion-blueviolet" +
        "?logo=kotlin&logoColor=white)"
}

private fun Project.buildMavenRepoBadge(subproject: String, mavenRepo: MavenRepo): String {
    val label: String =
        if (properties["shouldGenerateVersionBadgePerProject"]?.toString()?.toBoolean() == true) {
            "$subproject - ${mavenRepo.name}"
        } else {
            mavenRepo.name
        }

    val labelPath = label.replace(" ", "%20")

    return if (label.contains("snapshot", ignoreCase = true)) {
        "[![$label]" +
            "($shieldsIoUrl/nexus/s/$projectGroup.$projectName/$subproject" +
            "?server=https%3A%2F%2Foss.sonatype.org%2F" +
            "&label=$labelPath)]" +
            "(https://oss.sonatype.org/content/repositories/snapshots/$libFolderUrl/$subproject/)"
    } else {
        "[![$label]" +
            "($shieldsIoUrl/maven-central/v/$projectGroup.$projectName/$subproject" +
            "?label=$labelPath)]" +
            "(https://repo1.maven.org/maven2/$libFolderUrl/$subproject/)"
    }
}

private fun Project.buildBuildBadge(): String {
    return "[![Build]" +
        "($shieldsIoUrl/github/workflow/status/$repoWithoutUrlPrefix/build-kotlin" +
        "?label=Build&logo=GitHub)]" +
        "($repoUrl/tree/main)"
}

private fun Project.buildAnalysisBadge(sonar: Sonar): String {
    val projectKey =
        properties["codeAnalysis.sonar.projectKey"] ?: "${group}:${properties["project.name"]}"
    return "[![${sonar.label}]" +
        "($shieldsIoUrl/sonar/${sonar.path}/$projectKey" +
        "?label=${sonar.label.replace(" ", "%20")}&logo=SonarCloud" +
        "&logoColor=white&server=https%3A%2F%2Fsonarcloud.io)]" +
        "(https://sonarcloud.io/dashboard?id=$projectKey)"
}

@OptIn(ExperimentalStdlibApi::class)
private fun Project.buildReadmeBadges(): List<String> =
    buildList {
        val kotlinBadge = readmeBadgesExtension.kotlin.get()
        val mavenCentralBadge = readmeBadgesExtension.mavenCentral.get()
        val snapshotsBadge = readmeBadgesExtension.snapshots.get()
        val buildBadge = readmeBadgesExtension.build.get()
        val coverageBadge = readmeBadgesExtension.coverage.get()
        val qualityBadge = readmeBadgesExtension.quality.get()
        val techDebtBadge = readmeBadgesExtension.techDebt.get()

        if (kotlinBadge) add(buildKotlinVersionBadge())

        if (properties["readmeBadges.allProjects"]?.toString()?.toBoolean() == true) {
            rootProject.subprojects.onEach {
                add("")
                if (mavenCentralBadge) add(buildMavenRepoBadge(it.name, MavenRepo.MavenCentral))
                if (snapshotsBadge) add(buildMavenRepoBadge(it.name, MavenRepo.Snapshot))
            }
        } else {
            val mainProjectValue: String =
                checkNotNull(properties["readmeBadges.mainProject"]?.toString()) {
                    "readmeBadges.mainProject is missing, add it to gradle.properties"
                }

            val mainProject: String? =
                rootProject.allprojects
                    .firstOrNull { project -> project.name == mainProjectValue }
                    ?.name

            checkNotNull(mainProject) {
                "The project defined in readmeBadges.mainProject is not found, check the name of it"
            }

            if (mavenCentralBadge) add(buildMavenRepoBadge(mainProject, MavenRepo.MavenCentral))
            if (snapshotsBadge) add(buildMavenRepoBadge(mainProject, MavenRepo.Snapshot))
        }

        add("")
        if (buildBadge) add(buildBuildBadge())
        if (coverageBadge) add(buildAnalysisBadge(Sonar.Coverage))
        if (qualityBadge) add(buildAnalysisBadge(Sonar.Quality))
        if (techDebtBadge) add(buildAnalysisBadge(Sonar.TechDebt))
        add("")
    }
        .removeDuplicateEmptyLines()
        .lines()
        .dropWhile(String::isBlank)
        .run { if (any(String::isNotBlank)) this else emptyList() }
