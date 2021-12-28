@file:Suppress("PackageDirectoryMismatch")

package com.javiersc.gradle.plugins.publish.internal

import com.javiersc.gradle.plugins.core.isSignificant
import com.javiersc.gradle.plugins.core.onlySignificantGradleProperty
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
                if (!isSignificant && onlySignificantGradleProperty) {
                    error("Only significant versions can be published (current: $version)")
                }
            }
        }

    rootProject.tasks {
        findByName("initializeSonatypeStagingRepository")?.dependsOn(checkIsSignificant)
    }

    tasks {
        findByName("publish")?.dependsOn(checkIsSignificant)

        findByName("publishToSonatype")?.dependsOn(checkIsSignificant)

        findByName("publishToMavenLocal")?.dependsOn(checkIsSignificant)

        findByName("publishPlugins")?.dependsOn(checkIsSignificant)
    }
}
