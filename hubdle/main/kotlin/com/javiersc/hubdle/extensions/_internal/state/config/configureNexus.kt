package com.javiersc.hubdle.extensions._internal.state.config

import com.javiersc.hubdle.properties.PropertyKey.Nexus
import com.javiersc.hubdle.properties.getProperty
import com.javiersc.hubdle.properties.getPropertyOrNull
import io.github.gradlenexus.publishplugin.NexusPublishExtension
import java.time.Duration
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

internal fun configureNexus(project: Project) {
    project.pluginManager.apply("io.github.gradle-nexus.publish-plugin")

    project.the<NexusPublishExtension>().apply {
        repositoryDescription.set("${project.rootProject.group} - ${project.rootProject.version}")

        repositories { container ->
            container.sonatype { repository ->
                val nexusUrl = project.getPropertyOrNull(Nexus.nexusUrl)
                val snapshotRepositoryUrl = project.getPropertyOrNull(Nexus.snapshotRepositoryUrl)
                if (nexusUrl != null && snapshotRepositoryUrl != null) {
                    repository.nexusUrl.set(project.uri(nexusUrl))
                    repository.snapshotRepositoryUrl.set(project.uri(snapshotRepositoryUrl))
                }
                repository.username.set(project.getProperty(Nexus.ossUser))
                repository.password.set(project.getProperty(Nexus.ossToken))
                repository.stagingProfileId.set(project.getProperty(Nexus.ossStagingProfileId))
            }
        }

        connectTimeout.set(Duration.ofMinutes(30))
        clientTimeout.set(Duration.ofMinutes(30))

        transitionCheckOptions { options ->
            options.maxRetries.set(200)
            options.delayBetween.set(Duration.ofSeconds(10))
        }
    }
}
