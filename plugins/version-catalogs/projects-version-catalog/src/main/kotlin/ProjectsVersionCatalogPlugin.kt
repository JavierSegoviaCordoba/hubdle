package com.javiersc.gradle.plugins.projects.version.catalog

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

abstract class ProjectsVersionCatalogPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.createProjectsVersionCatalogExtension()

        val extension = project.projectsVersionCatalogExtension
        project.configureProjectsVersionCatalogExtension()

        project.rootProject.tasks.register<BuildVersionCatalogTask>(BuildVersionCatalogTask.name) {
            librariesPrefix = extension.librariesPrefix.get()
            projects = extension.projects.get()
            removeVersionAliasPrefix = extension.removeVersionAliasPrefix.get()
            tomlPath = extension.tomlPath.get()
        }
    }
}
