package com.javiersc.hubdle.extensions.config.documentation.readme._internal

import com.javiersc.gradle.properties.extensions.getProperty
import com.javiersc.gradle.properties.extensions.getPropertyOrNull
import com.javiersc.hubdle.HubdleProperty
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.documentation.readme.WriteReadmeBadgesTask
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

internal fun configureReadmeBadges(project: Project) {
    val readmeState = project.hubdleState.config.documentation.readme

    if (readmeState.badges.isEnabled) {

        val projectName =
            project.getPropertyOrNull(HubdleProperty.Project.mainProjectName) ?: project.name

        val projectKey =
            project.getPropertyOrNull(HubdleProperty.Analysis.projectKey)
                ?: "${project.group}:${project.getPropertyOrNull(HubdleProperty.Project.rootProjectDirName) ?: project.rootDir.name}"

        project.tasks.register<WriteReadmeBadgesTask>(WriteReadmeBadgesTask.name).configure {
            this.projectGroup.set(project.group.toString())
            this.projectName.set(projectName)
            this.repoUrl.set(project.getProperty(HubdleProperty.POM.scmUrl))
            this.kotlinVersion.set(project.getKotlinPluginVersion())
            this.kotlinBadge.set(readmeState.badges.kotlin)
            this.mavenCentralBadge.set(readmeState.badges.mavenCentral)
            this.snapshotsBadge.set(readmeState.badges.snapshots)
            this.buildBadge.set(readmeState.badges.build)
            this.coverageBadge.set(readmeState.badges.coverage)
            this.qualityBadge.set(readmeState.badges.quality)
            this.techDebtBadge.set(readmeState.badges.techDebt)
            this.projectKey.set(projectKey)
        }
    }
}
