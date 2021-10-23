package com.javiersc.gradle.plugins.projects.version.catalog.internal

import org.gradle.api.Project
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.kotlin.dsl.hasPlugin

val Project.projectsWithMavenPublishPlugin: List<Project>
    get() =
        rootProject.allprojects.toList().mapNotNull { project ->
            if (project.plugins.hasPlugin(MavenPublishPlugin::class)) project else null
        }
