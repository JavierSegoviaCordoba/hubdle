package com.javiersc.gradle.plugins.dependency.updates

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class DependencyUpdatesPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply("com.github.ben-manes.versions")

        target.tasks.withType(DependencyUpdatesTask::class.java) { task ->
            task.rejectVersionIf { !target.project.isStable(it.candidate.version) }
        }
    }
}

private fun Project.isStable(version: String): Boolean {
    val limit = "${properties["dependencyDiscoveryLimit"] ?: ""}"
    val filters =
        mutableListOf("SNAPSHOT", "dev", "eap", "alpha", "beta", "(M|milestone)(\\.?)[0-9]+", "rc")
            .apply {
                val indexToRemove =
                    if (limit == "milestone") indexOf("(M|milestone)(\\.?)[0-9]+")
                    else indexOf(limit)
                if (indexToRemove != -1) subList(indexToRemove, size).clear()
            }
    return !Regex(filters.joinToString("|"), RegexOption.IGNORE_CASE).containsMatchIn(version)
}
