package com.javiersc.gradle.plugins.all.projects

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.language.base.plugins.LifecycleBasePlugin

abstract class AllProjectsPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply(LifecycleBasePlugin::class.java)

        target.allprojects { project ->
            project.group = project.module

            project.pluginManager.apply("com.adarshr.test-logger")

            project.tasks.withType(Test::class.java) { test ->
                test.testLogging.showStandardStreams = true
                test.maxParallelForks =
                    (Runtime.getRuntime().availableProcessors() / 3).takeIf { it > 0 } ?: 1
                test.useJUnitPlatform()
            }
        }
    }
}
