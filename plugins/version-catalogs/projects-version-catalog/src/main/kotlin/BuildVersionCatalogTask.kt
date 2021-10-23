package com.javiersc.gradle.plugins.projects.version.catalog

import com.javiersc.gradle.plugins.projects.version.catalog.internal.PropertyDefaultValue
import com.javiersc.gradle.plugins.projects.version.catalog.models.toToml
import com.javiersc.gradle.plugins.projects.version.catalog.models.toVersionCatalog
import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

abstract class BuildVersionCatalogTask : DefaultTask() {

    init {
        group = "build"
    }

    @get:Input
    @set:Option(option = "librariesPrefix", description = "Prefix used in libraries aliases")
    @Optional
    var librariesPrefix: String = PropertyDefaultValue.librariesPrefix

    @get:Input
    @set:Option(option = "projects", description = "Projects used to generate the catalog")
    @Optional
    var projects: List<Project> = PropertyDefaultValue.projects(project)

    @get:Input
    @set:Option(
        option = "removeVersionAliasPrefix",
        description = "Remove the the prefix in the version alias"
    )
    @Optional
    var removeVersionAliasPrefix: String = PropertyDefaultValue.removeVersionAliasPrefix

    @get:Input
    @set:Option(option = "tomlPath", description = "Toml file path")
    @Optional
    var tomlPath: String = PropertyDefaultValue.tomlPath(project)

    @TaskAction
    fun run() {
        File(tomlPath).apply {
            parentFile.mkdirs()
            createNewFile()
            writeText(
                projects.toVersionCatalog(librariesPrefix, removeVersionAliasPrefix, this).toToml()
            )
        }
    }

    companion object {
        const val name = "buildProjectsVersionCatalog"
    }
}
