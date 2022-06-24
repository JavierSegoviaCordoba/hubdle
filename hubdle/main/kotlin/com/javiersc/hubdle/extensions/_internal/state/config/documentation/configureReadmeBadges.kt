package com.javiersc.hubdle.extensions._internal.state.config.documentation

import com.javiersc.hubdle.extensions._internal.state.hubdleState
import com.javiersc.hubdle.extensions.config.documentation.readme.WriteReadmeBadgesTask
import com.javiersc.hubdle.properties.PropertyKey
import com.javiersc.hubdle.properties.getProperty
import com.javiersc.hubdle.properties.getPropertyOrNull
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

internal fun configureReadmeBadges(project: Project) {
    val readmeBadgesState = project.hubdleState.config.documentation.readmeBadges

    if (readmeBadgesState.isEnabled) {

        val projectName =
            project.getPropertyOrNull(PropertyKey.Documentation.readmeBadgesMainProject)
                ?: project.name

        val projectKey =
            with(project) {
                "$group:${getPropertyOrNull(PropertyKey.Project.rootProjectName) ?: rootDir.name}"
            }

        project.tasks.register<WriteReadmeBadgesTask>(WriteReadmeBadgesTask.name).configure {
            it.projectGroup.set(project.group.toString())
            it.projectName.set(projectName)
            it.repoUrl.set(project.getProperty(PropertyKey.POM.scmUrl))
            it.kotlinVersion.set(project.getKotlinPluginVersion())
            it.kotlinBadge.set(readmeBadgesState.kotlin)
            it.mavenCentralBadge.set(readmeBadgesState.mavenCentral)
            it.snapshotsBadge.set(readmeBadgesState.snapshots)
            it.buildBadge.set(readmeBadgesState.build)
            it.coverageBadge.set(readmeBadgesState.coverage)
            it.qualityBadge.set(readmeBadgesState.quality)
            it.techDebtBadge.set(readmeBadgesState.techDebt)
            it.projectKey.set(projectKey)
        }
    }
}
