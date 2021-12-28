package com.javiersc.gradle.plugins.projects.version.catalog.internal

import org.gradle.api.Project

internal object PropertyDefaultValue {
    internal const val librariesPrefix = ""
    internal const val removeVersionAliasPrefix = ""
    internal fun tomlPath(project: Project) = "${project.projectDir}/projects.versions.toml"
    internal fun projects(project: Project): List<Project> = project.projectsWithMavenPublishPlugin
}
