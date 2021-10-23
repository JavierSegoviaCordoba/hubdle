package com.javiersc.gradle.plugins.projects.version.catalog

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

abstract class ProjectsVersionCatalogPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.createProjectsVersionCatalogExtension()

        val extension = project.projectsVersionCatalogExtension

        project.configureProjectsVersionCatalogExtension()

        val buildVersionCatalogTask =
            project.tasks.register<BuildVersionCatalogTask>(BuildVersionCatalogTask.name) {
                librariesPrefix = extension.librariesPrefix.get()
                projects = extension.projects.get()
                removeVersionAliasPrefix = extension.removeVersionAliasPrefix.get()
                tomlPath = extension.tomlPath.get()
            }

        val warningMessage = "Run `${BuildVersionCatalogTask.name}` to generate the Version Catalog"
        val generateCatalogAsTomlTask = project.tasks.findByName("generateCatalogAsToml")
        val assembleTask = project.tasks.findByName("assemble")

        when {
            generateCatalogAsTomlTask != null -> {
                generateCatalogAsTomlTask.dependsOn(buildVersionCatalogTask)
            }
            assembleTask != null -> assembleTask.dependsOn(buildVersionCatalogTask)
            else -> project.logger.warn(warningMessage)
        }
    }
}
