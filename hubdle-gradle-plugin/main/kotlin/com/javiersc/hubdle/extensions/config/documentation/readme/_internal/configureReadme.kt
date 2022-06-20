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
            with(project) {
                "$group:${getPropertyOrNull(HubdleProperty.Project.rootProjectDirName) ?: rootDir.name}"
            }

        project.tasks.register<WriteReadmeBadgesTask>(WriteReadmeBadgesTask.name).configure {
            it.projectGroup.set(project.group.toString())
            it.projectName.set(projectName)
            it.repoUrl.set(project.getProperty(HubdleProperty.POM.scmUrl))
            it.kotlinVersion.set(project.getKotlinPluginVersion())
            it.kotlinBadge.set(readmeState.badges.kotlin)
            it.mavenCentralBadge.set(readmeState.badges.mavenCentral)
            it.snapshotsBadge.set(readmeState.badges.snapshots)
            it.buildBadge.set(readmeState.badges.build)
            it.coverageBadge.set(readmeState.badges.coverage)
            it.qualityBadge.set(readmeState.badges.quality)
            it.techDebtBadge.set(readmeState.badges.techDebt)
            it.projectKey.set(projectKey)
        }
    }
}
