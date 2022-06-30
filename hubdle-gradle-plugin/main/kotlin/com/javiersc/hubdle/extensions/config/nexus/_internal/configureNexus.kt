package com.javiersc.hubdle.extensions.config.nexus._internal

import com.javiersc.gradle.properties.extensions.getPropertyOrNull
import com.javiersc.hubdle.HubdleProperty.Nexus
import com.javiersc.hubdle.extensions._internal.PluginIds
import com.javiersc.hubdle.extensions._internal.state.hubdleState
import io.github.gradlenexus.publishplugin.NexusPublishExtension
import java.time.Duration
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun configureNexus(project: Project) {
    if (project.hubdleState.config.nexus.isEnabled) {
        project.pluginManager.apply(PluginIds.Publishing.nexusPublish)

        project.configure<NexusPublishExtension> {
            repositoryDescription.set(
                "${project.rootProject.group} - ${project.rootProject.version}"
            )

            repositories { container ->
                container.sonatype { repository ->
                    val nexusUrl = project.getPropertyOrNull(Nexus.nexusUrl)
                    val snapshotRepositoryUrl =
                        project.getPropertyOrNull(Nexus.nexusSnapshotRepositoryUrl)
                    if (nexusUrl != null && snapshotRepositoryUrl != null) {
                        repository.nexusUrl.set(project.uri(nexusUrl))
                        repository.snapshotRepositoryUrl.set(project.uri(snapshotRepositoryUrl))
                    }
                    repository.username.set(project.getPropertyOrNull(Nexus.nexusUser))
                    repository.password.set(project.getPropertyOrNull(Nexus.nexusToken))
                    repository.stagingProfileId.set(
                        project.getPropertyOrNull(Nexus.nexusStagingProfileId)
                    )
                }
            }

            connectTimeout.set(Duration.ofMinutes(DEFAULT_CONNECT_TIMEOUT))
            clientTimeout.set(Duration.ofMinutes(DEFAULT_CLIENT_TIMEOUT))

            transitionCheckOptions { options ->
                options.maxRetries.set(DEFAULT_MAX_RETRIES)
                options.delayBetween.set(Duration.ofSeconds(DEFAULT_DELAY_BETWEEN))
            }
        }
    }
}

private const val DEFAULT_CONNECT_TIMEOUT = 30L
private const val DEFAULT_CLIENT_TIMEOUT = 30L
private const val DEFAULT_MAX_RETRIES = 200
private const val DEFAULT_DELAY_BETWEEN = 10L
