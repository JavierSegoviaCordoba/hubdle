package com.javiersc.gradle.plugins.projects.version.catalog.internal

import org.gradle.api.Project

internal object PropertyDefaultValue {
    internal const val librariesPrefix = ""
    internal const val removeVersionAliasPrefix = ""
    internal const val tomlPath = "gradle/projects.versions.toml"
    fun projects(project: Project): List<Project> = project.projectsWithMavenPublishPlugin
}
