package com.javiersc.gradle.plugins.readme.badges.generator

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

abstract class ReadmeBadgesGeneratorPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        check(target == target.rootProject) {
            "ReadmeBadgesGenerator plugin must be applied to the root project"
        }

        ReadmeBadgesGeneratorExtension.createExtension(target)

        BuildReadmeBadgesTask.register(target) {
            projectGroup.set(
                checkNotNull(project.properties["allProjects.group"]) {
                        "`allProjects.group` in `gradle.properties` is missing"
                    }
                    .toString()
            )
            projectName.set(
                checkNotNull(project.properties["allProjects.name"]) {
                        "`allProjects.name` in `gradle.properties` is missing"
                    }
                    .toString()
            )
            repoUrl.set(
                checkNotNull(project.properties["pom.smc.url"]) {
                        "`pom.smc.url` in `gradle.properties` is missing"
                    }
                    .toString()
            )
            kotlinVersion.set(
                checkNotNull(
                    project.allprojects
                        .asSequence()
                        .map { project -> project.getKotlinPluginVersion() }
                        .toSet()
                        .firstOrNull()
                ) { "Kotlin Gradle plugin is not being applied to any project" }
            )
            kotlinBadge.set(project.readmeBadgesExtension.kotlin)
            mavenCentralBadge.set(project.readmeBadgesExtension.mavenCentral)
            snapshotsBadge.set(project.readmeBadgesExtension.snapshots)
            buildBadge.set(project.readmeBadgesExtension.build)
            coverageBadge.set(project.readmeBadgesExtension.coverage)
            qualityBadge.set(project.readmeBadgesExtension.quality)
            techDebtBadge.set(project.readmeBadgesExtension.techDebt)
            projectKey.set(
                (project.properties["codeAnalysis.sonar.projectKey"]
                        ?: "${project.group}:${project.properties["project.name"]}")
                    .toString()
            )

            allProjects.set(
                project.properties["readmeBadges.allProjects"]?.toString()?.toBoolean() ?: false
            )
            mainProject.set(
                checkNotNull(
                    project.allprojects
                        .firstOrNull { project ->
                            project.name ==
                                project.properties["readmeBadges.mainProject"]?.toString()
                        }
                        ?.name
                ) {
                    "The project defined in readmeBadges.mainProject is not found, add it to gradle.properties"
                }
            )
            shouldGenerateVersionBadgePerProject.set(
                project.properties["shouldGenerateVersionBadgePerProject"]?.toString()?.toBoolean()
                    ?: false
            )
            subprojectsNames.set(project.subprojects.map(Project::getName))
        }
    }
}
