package com.javiersc.hubdle.project.extensions.config.publishing.tasks

import PUBLISH_TO_MAVEN_LOCAL_TEST
import com.javiersc.hubdle.project.extensions.config.publishing.HubdleConfigPublishingExtension
import com.javiersc.hubdle.project.extensions.config.publishing.isSignificantSemver
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register

public abstract class CheckIsSemverTask : DefaultTask() {

    @get:Input public abstract val hasPublishToMavenLocalTest: Property<Boolean>

    @get:Input public abstract val publishNonSemver: Property<Boolean>

    @get:Input public abstract val versionIsSignificant: Property<Boolean>

    @get:Input public abstract val version: Property<String>

    @TaskAction
    public fun run() {
        val isPublishException: Boolean = publishNonSemver.get() || hasPublishToMavenLocalTest.get()
        check(versionIsSignificant.get() || isPublishException) {
            """|Only semantic versions can be published (current: ${version.get()})
               |Use `"-Ppublishing.nonSemver=true"` to force the publication 
            """
                .trimMargin()
        }
    }

    public companion object {

        public const val NAME: String = "checkIsSemver"

        internal fun register(publishing: HubdleConfigPublishingExtension) {
            val project: Project = publishing.project
            project.tasks.register<CheckIsSemverTask>(NAME).configure { task ->
                val taskGraphNames: Provider<List<String>> =
                    project.provider { project.gradle.taskGraph.allTasks.map(Task::getName) }

                val hasPublishToMavenLocalTest: Provider<Boolean> =
                    project.provider {
                        taskGraphNames.get().any { it == PUBLISH_TO_MAVEN_LOCAL_TEST }
                    }

                task.hasPublishToMavenLocalTest.set(hasPublishToMavenLocalTest)
                task.publishNonSemver.set(publishing.publishNonSemver)
                task.versionIsSignificant.set(project.provider { project.isSignificantSemver })
                task.version.set(project.provider { "${project.version}" })
            }
        }
    }
}
