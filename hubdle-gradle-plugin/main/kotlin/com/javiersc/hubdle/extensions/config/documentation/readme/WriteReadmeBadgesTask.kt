@file:OptIn(ExperimentalStdlibApi::class)

package com.javiersc.hubdle.extensions.config.documentation.readme

import com.javiersc.hubdle.extensions.config.documentation.readme._internal.models.MavenRepo
import com.javiersc.hubdle.extensions.config.documentation.readme._internal.models.Sonar
import com.javiersc.kotlin.stdlib.removeDuplicateEmptyLines
import java.io.File
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.file.ProjectLayout
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction

@CacheableTask
public abstract class WriteReadmeBadgesTask
@Inject
constructor(
    private val layout: ProjectLayout,
) : DefaultTask() {

    init {
        group = "documentation"
    }

    @get:InputFile
    @get:PathSensitive(PathSensitivity.ABSOLUTE)
    public val readmeFile: File
        get() = File("${layout.projectDirectory.asFile}/README.md")

    @get:Input public abstract val projectGroup: Property<String>

    @get:Input public abstract val projectName: Property<String>

    @get:Input public abstract val repoUrl: Property<String>

    @get:Input public abstract val kotlinVersion: Property<String>

    @get:Input public abstract val kotlinBadge: Property<Boolean>

    @get:Input public abstract val mavenCentralBadge: Property<Boolean>

    @get:Input public abstract val snapshotsBadge: Property<Boolean>

    @get:Input public abstract val buildBadge: Property<Boolean>

    @get:Input public abstract val coverageBadge: Property<Boolean>

    @get:Input public abstract val qualityBadge: Property<Boolean>

    @get:Input public abstract val techDebtBadge: Property<Boolean>

    @get:Input public abstract val projectKey: Property<String>

    @get:OutputFile
    public val outputReadmeFile: File
        get() = readmeFile

    @TaskAction
    public fun build() {
        outputReadmeFile.apply {
            val content: List<String> = readLines()
            val updatedContent: List<String> = buildList {
                addAll(buildReadmeBadges())
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

    public companion object {
        public const val name: String = "writeReadmeBadges"
    }

    private val buildKotlinVersionBadge: String
        get() =
            "![Kotlin version]" +
                "($shieldsIoUrl/badge/kotlin-${kotlinVersion.get()}-blueviolet" +
                "?logo=kotlin&logoColor=white)".also {
                    logger.lifecycle("Kotlin version: ${kotlinVersion.get()}")
                }

    private val shieldsIoUrl = "https://img.shields.io"

    private val repoWithoutUrlPrefix: String
        get() = repoUrl.get().replace("https://github.com/", "")

    private fun buildMavenRepoBadge(mavenRepo: MavenRepo): String {
        val label: String = mavenRepo.name

        val labelPath = label.replace(" ", "%20")

        val groupPath = projectGroup.get().replace(".", "/")
        val namePath = projectName.get()

        return if (label.contains("snapshot", ignoreCase = true)) {
            "[![$label]" +
                "($shieldsIoUrl/nexus/s/${projectGroup.get()}/$namePath" +
                "?server=https%3A%2F%2Foss.sonatype.org%2F" +
                "&label=$labelPath)]" +
                "(https://oss.sonatype.org/content/repositories/snapshots/$groupPath/$namePath/)"
        } else {
            "[![$label]" +
                "($shieldsIoUrl/maven-central/v/${projectGroup.get()}/$namePath" +
                "?label=$labelPath)]" +
                "(https://repo1.maven.org/maven2/$groupPath/$namePath/)"
        }
    }

    private fun buildBuildBadge(): String {
        return "[![Build]" +
            "($shieldsIoUrl/github/workflow/status/$repoWithoutUrlPrefix/build-kotlin" +
            "?label=Build&logo=GitHub)]" +
            "(${repoUrl.get()}/tree/main)"
    }

    private fun buildAnalysisBadge(sonar: Sonar): String =
        "[![${sonar.label}]" +
            "($shieldsIoUrl/sonar/${sonar.path}/${projectKey.get()}" +
            "?label=${sonar.label.replace(" ", "%20")}&logo=SonarCloud" +
            "&logoColor=white&server=https%3A%2F%2Fsonarcloud.io)]" +
            "(https://sonarcloud.io/dashboard?id=${projectKey.get()})"

    private fun buildReadmeBadges(): List<String> =
        buildList {
                if (kotlinBadge.get()) add(buildKotlinVersionBadge)

                if (mavenCentralBadge.get()) add(buildMavenRepoBadge(MavenRepo.MavenCentral))

                if (snapshotsBadge.get()) add(buildMavenRepoBadge(MavenRepo.Snapshot))

                add("")
                if (buildBadge.get()) add(buildBuildBadge())
                if (coverageBadge.get()) add(buildAnalysisBadge(Sonar.Coverage))
                if (qualityBadge.get()) add(buildAnalysisBadge(Sonar.Quality))
                if (techDebtBadge.get()) add(buildAnalysisBadge(Sonar.TechDebt))
                add("")
            }
            .removeDuplicateEmptyLines()
            .lines()
            .dropWhile(String::isBlank)
            .run { if (any(String::isNotBlank)) this else emptyList() }
}
