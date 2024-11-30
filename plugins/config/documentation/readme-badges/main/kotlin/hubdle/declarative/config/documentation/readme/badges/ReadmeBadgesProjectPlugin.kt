@file:Suppress("UnstableApiUsage")

package hubdle.declarative.config.documentation.readme.badges

import hubdle.declarative.config.documentation.readme.badges.MavenRepo.MavenCentral
import hubdle.declarative.config.documentation.readme.badges.MavenRepo.Snapshot
import hubdle.declarative.platform.PlatformProperties as PP
import hubdle.declarative.platform.platformFactory
import hubdle.declarative.platform.property
import hubdle.declarative.platform.withJetBrainsKotlinPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.plugins.software.SoftwareType
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.declarative.dsl.model.annotations.Restricted
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

public abstract class ReadmeBadgesProjectPlugin : Plugin<Project> {

    @get:SoftwareType(name = "readmeBadges", modelPublicType = ReadmeBadges::class)
    public abstract val readmeBadges: ReadmeBadges

    override fun apply(target: Project) {
        target.logger.quiet("Readme badges plugin applied")

        target.link(readmeBadges)

        WriteReadmeBadgesTask.register(target, readmeBadges)
    }
}

@Restricted
public interface ReadmeBadges {

    @get:Restricted public val readmeFile: Property<String>
    @get:Restricted public val projectGroup: Property<String>
    @get:Restricted public val projectName: Property<String>
    @get:Restricted public val scmUrl: Property<String>

    @get:Restricted public val kotlinBadgeVersion: Property<String>
    @get:Restricted public val kotlinBadgeEnabled: Property<Boolean>
    @get:Restricted public val kotlinBadge: Property<String>

    @get:Restricted public val mavenCentralBadgeEnabled: Property<Boolean>
    @get:Restricted public val mavenCentralBadge: Property<String>

    @get:Restricted public val snapshotsBadgeEnabled: Property<Boolean>
    @get:Restricted public val snapshotsBadge: Property<String>

    @get:Restricted public val buildBadgeEnabled: Property<Boolean>
    @get:Restricted public val buildBadge: Property<String>

    @get:Restricted public val coverageBadgeEnabled: Property<Boolean>
    @get:Restricted public val coverageBadge: Property<String>

    @get:Restricted public val qualityBadgeEnabled: Property<Boolean>
    @get:Restricted public val qualityBadge: Property<String>

    @get:Restricted public val techDebtBadgeEnabled: Property<Boolean>
    @get:Restricted public val techDebtBadge: Property<String>
}

internal fun Project.link(badges: ReadmeBadges) = platformFactory {
    badges.readmeFile.set(layout.projectDirectory.file("README.md").asFile.path)
    badges.projectGroup.set(property(getProperty(PP.ProjectGroup)).orElse("${project.group}"))
    badges.projectName.set(property(getProperty(PP.ProjectName)).orElse(project.name))
    badges.scmUrl.set(property(getProperty(PP.Pom.url)))

    badges.kotlinBadgeEnabled.set(property { false })
    withJetBrainsKotlinPlugin {
        badges.kotlinBadgeVersion.set(provider { getKotlinPluginVersion(logger) })
        badges.kotlinBadge.set(property { badges.buildKotlinVersionBadge() })
    }

    badges.mavenCentralBadgeEnabled.set(property { false })
    badges.mavenCentralBadge.set(property { badges.buildMavenRepoBadge(MavenCentral) })

    badges.snapshotsBadgeEnabled.set(property { false })
    badges.snapshotsBadge.set(property { badges.buildMavenRepoBadge(Snapshot) })

    badges.buildBadgeEnabled.set(property { false })
    badges.buildBadge.set(property { badges.buildBuildBadge() })

    badges.coverageBadgeEnabled.set(property { false })
    // TODO: readmeBadges.coverageBadge.set(property { buildAnalysisBadge(Sonar.Coverage) })

    badges.qualityBadgeEnabled.set(property { false })
    // TODO: readmeBadges.qualityBadge.set(property { buildAnalysisBadge(Sonar.Quality) })

    badges.techDebtBadgeEnabled.set(property { false })
    // TODO: readmeBadges.techDebtBadge.set(property { buildAnalysisBadge(Sonar.TechDebt) })
}

private const val ShieldsIoUrl = "https://img.shields.io"

private fun ReadmeBadges.buildKotlinVersionBadge(): String =
    "![Kotlin version]" +
        "($ShieldsIoUrl/badge/kotlin-${kotlinBadgeVersion.get()}-blueviolet" +
        "?logo=kotlin&logoColor=white)"

private fun ReadmeBadges.buildMavenRepoBadge(mavenRepo: MavenRepo): String {
    val label: String = mavenRepo.name

    val groupPath: String = projectGroup.get().replace(".", "/")
    val namePath: String = projectName.get()

    return when (mavenRepo) {
        MavenCentral -> {
            "[![$label]" +
                "($ShieldsIoUrl/maven-central/v/${projectGroup.get()}/$namePath" +
                "?label=$label)]" +
                "(https://repo1.maven.org/maven2/$groupPath/$namePath/)"
        }
        Snapshot -> {
            "[![$label]" +
                "($ShieldsIoUrl/nexus/s/${projectGroup.get()}/$namePath" +
                "?server=https://oss.sonatype.org/" +
                "&label=$label)]" +
                "(https://oss.sonatype.org/content/repositories/snapshots/$groupPath/$namePath/)"
        }
    }
}

private fun ReadmeBadges.buildBuildBadge(): String {
    return "[![Build]" +
        "($ShieldsIoUrl/github/actions/workflow/status/$scmUrlPrefix/build-kotlin.yaml" +
        "?label=Build&logo=GitHub)]" +
        "(${scmUrlWithoutSlashAtEnd}/tree/main)"
}

private fun buildAnalysisBadge(sonar: Sonar, projectKey: Provider<String>): String =
    "[![${sonar.label}]" +
        "($ShieldsIoUrl/sonar/${sonar.path}/${projectKey.get()}" +
        "?label=${sonar.label.replace(" ", "%20")}&logo=SonarCloud" +
        "&logoColor=white&server=https%3A%2F%2Fsonarcloud.io)]" +
        "(https://sonarcloud.io/dashboard?id=${projectKey.get()})"

private val ReadmeBadges.scmUrlPrefix: String
    get() = scmUrlWithoutSlashAtEnd.replace("https://github.com/", "")

private val ReadmeBadges.scmUrlWithoutSlashAtEnd: String
    get() = scmUrl.get().dropLastWhile { it == '/' }

private enum class MavenRepo {
    MavenCentral,
    Snapshot,
}

private enum class Sonar(val label: String, val path: String) {
    Coverage("Coverage", "coverage"),
    Quality("Quality", "quality_gate"),
    TechDebt("Tech debt", "tech_debt"),
}
