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

        project.tasks.register<WriteReadmeBadgesTask>(WriteReadmeBadgesTask.name).configure { task
            ->
            task.projectGroup.set(project.group.toString())
            task.projectName.set(projectName)
            task.repoUrl.set(project.getProperty(HubdleProperty.POM.scmUrl))
            task.kotlinVersion.set(project.getKotlinPluginVersion())
            task.kotlinBadge.set(readmeState.badges.kotlin)
            task.mavenCentralBadge.set(readmeState.badges.mavenCentral)
            task.snapshotsBadge.set(readmeState.badges.snapshots)
            task.buildBadge.set(readmeState.badges.build)
            task.coverageBadge.set(readmeState.badges.coverage)
            task.qualityBadge.set(readmeState.badges.quality)
            task.techDebtBadge.set(readmeState.badges.techDebt)
            task.projectKey.set(projectKey)
        }
    }
}
