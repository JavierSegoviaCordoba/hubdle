@file:OptIn(ExperimentalStdlibApi::class)

package com.javiersc.gradle.plugins.readme.badges.generator

import com.javiersc.gradle.plugins.readme.badges.generator.models.MavenRepo
import com.javiersc.gradle.plugins.readme.badges.generator.models.Sonar
import com.javiersc.kotlin.stdlib.removeDuplicateEmptyLines
import java.io.File
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.ProjectLayout
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.UntrackedTask
import org.gradle.kotlin.dsl.register

@UntrackedTask(because = "The input and output files are the same file (README.md)")
abstract class BuildReadmeBadgesTask
@Inject
constructor(
    private val layout: ProjectLayout,
) : DefaultTask() {

    @get:InputFile
    @get:PathSensitive(PathSensitivity.ABSOLUTE)
    val readmeFile: File
        get() = File("${layout.projectDirectory.asFile}/README.md")

    @get:Input abstract val projectGroup: Property<String>

    @get:Input abstract val projectName: Property<String>

    @get:Input abstract val repoUrl: Property<String>

    @get:Input abstract val kotlinVersion: Property<String>

    @get:Input abstract val kotlinBadge: Property<Boolean>

    @get:Input abstract val mavenCentralBadge: Property<Boolean>

    @get:Input abstract val snapshotsBadge: Property<Boolean>

    @get:Input abstract val buildBadge: Property<Boolean>

    @get:Input abstract val coverageBadge: Property<Boolean>

    @get:Input abstract val qualityBadge: Property<Boolean>

    @get:Input abstract val techDebtBadge: Property<Boolean>

    @get:Input abstract val projectKey: Property<String>

    @get:Input abstract val allProjects: Property<Boolean>

    @get:Input abstract val mainProject: Property<String>

    @get:Input abstract val shouldGenerateVersionBadgePerProject: Property<Boolean>

    @get:Input abstract val subprojectsNames: ListProperty<String>

    @get:OutputFile
    val outputReadmeFile: File
        get() = readmeFile

    @TaskAction
    fun build() {
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

    companion object {
        const val name = "buildReadmeBadges"
        const val taskGroup = "documentation"

        fun register(
            project: Project,
            configure: BuildReadmeBadgesTask.() -> Unit,
        ) {
            project.tasks.register<BuildReadmeBadgesTask>(name) {
                group = taskGroup
                configure(this)
            }
        }
    }

    private val buildKotlinVersionBadge: String
        get() =
            "![Kotlin version]" +
                "($shieldsIoUrl/badge/kotlin-${kotlinVersion.get()}-blueviolet" +
                "?logo=kotlin&logoColor=white)".also {
                    logger.lifecycle("Kotlin version: ${kotlinVersion.get()}")
                }

    private val shieldsIoUrl = "https://img.shields.io"

    private val libFolderUrl: String
        get() = "${projectGroup.get()}/${projectName.get()}".replace(".", "/")

    private val repoWithoutUrlPrefix: String
        get() = repoUrl.get().replace("https://github.com/", "")

    private fun buildMavenRepoBadge(subproject: String, mavenRepo: MavenRepo): String {
        val label: String =
            if (shouldGenerateVersionBadgePerProject.get()) "$subproject - ${mavenRepo.name}"
            else mavenRepo.name

        val labelPath = label.replace(" ", "%20")

        return if (label.contains("snapshot", ignoreCase = true)) {
            "[![$label]" +
                "($shieldsIoUrl/nexus/s/${projectGroup.get()}.${projectName.get()}/$subproject" +
                "?server=https%3A%2F%2Foss.sonatype.org%2F" +
                "&label=$labelPath)]" +
                "(https://oss.sonatype.org/content/repositories/snapshots/$libFolderUrl/$subproject/)"
        } else {
            "[![$label]" +
                "($shieldsIoUrl/maven-central/v/${projectGroup.get()}.${projectName.get()}/$subproject" +
                "?label=$labelPath)]" +
                "(https://repo1.maven.org/maven2/$libFolderUrl/$subproject/)"
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

                if (allProjects.get()) {
                    subprojectsNames.get().onEach { subprojectName ->
                        add("")
                        if (mavenCentralBadge.get()) {
                            add(buildMavenRepoBadge(subprojectName, MavenRepo.MavenCentral))
                        }
                        if (snapshotsBadge.get()) {
                            add(buildMavenRepoBadge(subprojectName, MavenRepo.Snapshot))
                        }
                    }
                } else {
                    if (mavenCentralBadge.get()) {
                        add(buildMavenRepoBadge(mainProject.get(), MavenRepo.MavenCentral))
                    }
                    if (snapshotsBadge.get()) {

                        add(buildMavenRepoBadge(mainProject.get(), MavenRepo.Snapshot))
                    }
                }

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
