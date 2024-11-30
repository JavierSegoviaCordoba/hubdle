package hubdle.declarative.config.documentation.readme.badges

import com.javiersc.kotlin.stdlib.removeDuplicateEmptyLines
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity.RELATIVE
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.gradle.kotlin.dsl.register

@CacheableTask
public abstract class WriteReadmeBadgesTask : DefaultTask() {

    init {
        group = "documentation"
    }

    @get:InputFile
    @get:PathSensitive(RELATIVE)
    public abstract val readmeInputFile: RegularFileProperty

    @get:Input public abstract val projectGroup: Property<String>
    @get:Input public abstract val projectName: Property<String>
    @get:Input public abstract val scmUrl: Property<String>

    @get:Input public abstract val kotlinBadgeEnabled: Property<Boolean>
    @get:Input @get:Optional public abstract val kotlinBadgeVersion: Property<String>
    @get:Input @get:Optional public abstract val kotlinBadge: Property<String>

    @get:Input public abstract val mavenCentralBadgeEnabled: Property<Boolean>
    @get:Input @get:Optional public abstract val mavenCentralBadge: Property<String>

    @get:Input public abstract val snapshotsBadgeEnabled: Property<Boolean>
    @get:Input @get:Optional public abstract val snapshotsBadge: Property<String>

    @get:Input public abstract val buildBadgeEnabled: Property<Boolean>
    @get:Input @get:Optional public abstract val buildBadge: Property<String>

    @get:Input public abstract val coverageBadgeEnabled: Property<Boolean>
    @get:Input @get:Optional public abstract val coverageBadge: Property<String>

    @get:Input public abstract val qualityBadgeEnabled: Property<Boolean>
    @get:Input @get:Optional public abstract val qualityBadge: Property<String>

    @get:Input public abstract val techDebtBadgeEnabled: Property<Boolean>
    @get:Input @get:Optional public abstract val techDebtBadge: Property<String>

    @get:Input
    @get:Option(option = "write", description = "Write the badges in the README.md file")
    public abstract val write: Property<Boolean>

    @get:OutputFile public abstract val readmeOutputFile: RegularFileProperty

    @TaskAction
    public fun build() {
        readmeOutputFile.get().asFile.apply {
            val content: List<String> = readLines()
            val updatedContent: List<String> = buildList {
                addAll(buildReadmeBadges())
                addAll(
                    content.subList(
                        content.indexOfFirst { it.contains("# ") },
                        content.lastIndex + 1,
                    )
                )
                add("")
            }
            val updatedContentAsString: String = updatedContent.joinToString(separator = "\n")
            logger.quiet("Updated README.md content:")
            logger.quiet("--------------------------")
            logger.quiet(updatedContentAsString)
            logger.quiet("--------------------------")
            if (write.get()) writeText(updatedContentAsString)
        }
    }

    public companion object {
        public const val NAME: String = "writeReadmeBadges"

        internal fun register(project: Project, badges: ReadmeBadges) {
            project.tasks.register<WriteReadmeBadgesTask>(NAME) {
                readmeInputFile.set(project.layout.projectDirectory.file(badges.readmeFile))

                projectGroup.set(badges.projectGroup)
                projectName.set(badges.projectName)
                scmUrl.set(badges.scmUrl)

                kotlinBadgeEnabled.set(badges.kotlinBadgeEnabled)
                kotlinBadgeVersion.set(badges.kotlinBadgeVersion)
                kotlinBadge.set(badges.kotlinBadge)

                mavenCentralBadgeEnabled.set(badges.mavenCentralBadgeEnabled)
                mavenCentralBadge.set(badges.mavenCentralBadge)

                snapshotsBadgeEnabled.set(badges.snapshotsBadgeEnabled)
                snapshotsBadge.set(badges.snapshotsBadge)

                buildBadgeEnabled.set(badges.buildBadgeEnabled)
                buildBadge.set(badges.buildBadge)

                coverageBadgeEnabled.set(badges.coverageBadgeEnabled)
                coverageBadge.set(badges.coverageBadge)

                qualityBadgeEnabled.set(badges.qualityBadgeEnabled)
                qualityBadge.set(badges.qualityBadge)

                techDebtBadgeEnabled.set(badges.techDebtBadgeEnabled)
                techDebtBadge.set(badges.techDebtBadge)

                write.set(true)

                readmeOutputFile.set(readmeInputFile)
            }
        }
    }

    private fun buildReadmeBadges(): List<String> =
        buildList {
                addBadge(kotlinBadgeEnabled, kotlinBadge)
                addBadge(mavenCentralBadgeEnabled, mavenCentralBadge)
                addBadge(snapshotsBadgeEnabled, snapshotsBadge)

                add("")

                addBadge(buildBadgeEnabled, buildBadge)
                addBadge(coverageBadgeEnabled, coverageBadge)
                addBadge(qualityBadgeEnabled, qualityBadge)
                addBadge(techDebtBadgeEnabled, techDebtBadge)

                add("")
            }
            .removeDuplicateEmptyLines()
            .lines()
            .dropWhile(String::isBlank)
            .run { if (any(String::isNotBlank)) this else emptyList() }

    private fun MutableList<String>.addBadge(enabled: Provider<Boolean>, badge: Property<String>) {
        if (enabled.get() && badge.isPresent) add(badge.get())
    }
}
