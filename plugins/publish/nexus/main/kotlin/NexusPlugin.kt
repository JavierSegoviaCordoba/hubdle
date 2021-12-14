package com.javiersc.gradle.plugins.nexus

import io.github.gradlenexus.publishplugin.NexusPublishExtension
import java.time.Duration
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class NexusPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply("io.github.gradle-nexus.publish-plugin")

        target.extensions.findByType(NexusPublishExtension::class.java)?.apply {
            repositoryDescription.set("${target.rootProject.group} - ${target.rootProject.version}")

            repositories { container ->
                container.sonatype { repository ->
                    repository.username.set(
                        "${target.properties["oss.user"] ?: System.getenv("OSS_USER")}"
                    )
                    repository.password.set(
                        "${target.properties["oss.token"] ?: System.getenv("OSS_TOKEN")}"
                    )
                    repository.stagingProfileId.set(
                        "${target.properties["oss.stagingProfileId"] ?: System.getenv("OSS_STAGING_PROFILE_ID")}",
                    )
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
}
