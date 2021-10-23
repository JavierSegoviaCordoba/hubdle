@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.gradle.plugins.publish.internal

import com.javiersc.plugins.core.isSignificant
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Exec
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke

internal fun Project.configurePublishOnlySignificant() {
    tasks { create<Exec>("gitDiff") { commandLine("git", "diff") } }

    val checkIsSignificant: Task by
        project.tasks.creating {
            dependsOn("gitDiff")
            doLast {
                if (!isSignificant) {
                    error("Only significant versions can be published (current: $version)")
                }
            }
        }

    tasks {
        getByName("initializeSonatypeStagingRepository") { dependsOn(checkIsSignificant) }

        getByName("publish") { dependsOn(checkIsSignificant) }

        getByName("publishToSonatype") { dependsOn(checkIsSignificant) }

        getByName("publishToMavenLocal") { dependsOn(checkIsSignificant) }

        getByName("publishPlugins") { dependsOn(checkIsSignificant) }
    }
}
