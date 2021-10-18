package com.javiersc.gradle.plugins.projects.version.catalog

import com.javiersc.gradle.plugins.projects.version.catalog.internal.PropertyDefaultValue
import org.gradle.api.Project
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType

interface ProjectsVersionCatalogExtension {
    val librariesPrefix: Property<String>
    val projects: ListProperty<Project>
    val tomlPath: Property<String>
    val removeVersionAliasPrefix: Property<String>
}

internal fun Project.createProjectsVersionCatalogExtension(): ProjectsVersionCatalogExtension =
    extensions.create("projectsVersionCatalog")

internal val Project.projectsVersionCatalogExtension: ProjectsVersionCatalogExtension
    get() = extensions.getByType()

internal fun Project.configureProjectsVersionCatalogExtension() {
    projectsVersionCatalogExtension.apply {
        tomlPath.convention(PropertyDefaultValue.tomlPath)
        rootProject.gradle.projectsEvaluated {
            projects.convention(PropertyDefaultValue.projects(project))
        }
        librariesPrefix.convention(PropertyDefaultValue.librariesPrefix)
        removeVersionAliasPrefix.convention(PropertyDefaultValue.removeVersionAliasPrefix)
    }
}
