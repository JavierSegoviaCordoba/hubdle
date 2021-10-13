package com.javiersc.gradle.plugins.projects.version.catalog.internal

import org.gradle.api.Project
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.hasPlugin
import org.gradle.plugin.devel.GradlePluginDevelopmentExtension
import org.gradle.plugin.devel.PluginDeclaration

val Project.projectsWithMavenPublishPlugin: List<Project>
    get() =
        rootProject.allprojects.toList().mapNotNull { project ->
            if (project.plugins.hasPlugin(MavenPublishPlugin::class)) project else null
        }

val Project.projectsWithGradlePlugin: List<Pair<Project, String?>>
    get() =
        rootProject.allprojects.toList().map { project: Project ->
            val extension = project.extensions.findByType<GradlePluginDevelopmentExtension>()
            val id = extension?.plugins?.asMap?.values?.map(PluginDeclaration::getId)?.firstOrNull()
            project to id
        }
